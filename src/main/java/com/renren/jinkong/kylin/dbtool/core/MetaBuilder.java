package com.renren.jinkong.kylin.dbtool.core;

import com.renren.jinkong.kylin.dbtool.core.dialect.Dialect;
import com.renren.jinkong.kylin.dbtool.core.dialect.MysqlDialect;
import com.renren.jinkong.kylin.dbtool.kit.StrKit;
import com.renren.jinkong.kylin.dbtool.model.ColumnMeta;
import com.renren.jinkong.kylin.dbtool.model.TableMeta;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

/**
 * MetaBuilder
 */
public class MetaBuilder {
	protected DataSource dataSource;
	protected Dialect dialect = new MysqlDialect();
	protected Connection conn = null;
	protected DatabaseMetaData dbMeta = null;
	protected TypeMapping typeMapping = new TypeMapping();

	public MetaBuilder(DataSource dataSource) {
		if (dataSource == null) {
			throw new IllegalArgumentException("dataSource can not be null.");
		}
		this.dataSource = dataSource;
	}

	public TableMeta build(String dbTableName) {
		try {
			System.out.println(dbTableName);
			conn = dataSource.getConnection();
			dbMeta = conn.getMetaData();

			TableMeta meta = new TableMeta();

			meta.setName(dbTableName);

			buildPrimaryKey(meta);
			buildColumnMetas(meta);
			buildColumnMetaOthers(meta);

			return meta;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (conn != null) {
				try {conn.close();} catch (SQLException e) {throw new RuntimeException(e);}
			}
		}
	}

	protected void buildColumnMetaOthers(TableMeta tableMeta) {
		List<ColumnMeta> columnMetas = tableMeta.getColumnMetas();
		columnMetas.forEach(columnMeta -> {
			try {
				buildColumnOtherAttr(tableMeta, columnMeta);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	protected void buildPrimaryKey(TableMeta tableMeta) throws SQLException {
		ResultSet rs = dbMeta.getPrimaryKeys(conn.getCatalog(), null, tableMeta.name);

		String primaryKey = "";
		int index = 0;
		while (rs.next()) {
			if (index++ > 0) {
				primaryKey += ",";
			}
			primaryKey += rs.getString("COLUMN_NAME");
		}
		if (StrKit.isBlank(primaryKey)) {
			throw new RuntimeException("primaryKey of table \"" + tableMeta.name + "\" required by active record pattern");
		}
		tableMeta.primaryKey = primaryKey;
		rs.close();
	}

	/**
	 * 文档参考：
	 * http://dev.mysql.com/doc/connector-j/en/connector-j-reference-type-conversions.html
	 *
	 * JDBC 与时间有关类型转换规则，mysql 类型到 java 类型如下对应关系：
	 * DATE				java.sql.Date
	 * DATETIME			java.sql.Timestamp
	 * TIMESTAMP[(M)]	java.sql.Timestamp
	 * TIME				java.sql.Time
	 *
	 * 对数据库的 DATE、DATETIME、TIMESTAMP、TIME 四种类型注入 new java.util.Date()对象保存到库以后可以达到“秒精度”
	 * 为了便捷性，getter、setter 方法中对上述四种字段类型采用 java.util.Date，可通过定制 TypeMapping 改变此映射规则
	 */
	protected void buildColumnMetas(TableMeta tableMeta) throws SQLException {
		String sql = dialect.forTableBuilderDoBuild(tableMeta.name);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();

		for (int i=1; i<=rsmd.getColumnCount(); i++) {
			String columnName = rsmd.getColumnName(i);
			if(!"id".equals(columnName)) {
				ColumnMeta cm = new ColumnMeta();
				cm.name = rsmd.getColumnName(i);

				String typeStr = null;
				if (dialect.isKeepByteAndShort()) {
					int type = rsmd.getColumnType(i);
					if (type == Types.TINYINT) {
						typeStr = "java.lang.Byte";
					} else if (type == Types.SMALLINT) {
						typeStr = "java.lang.Short";
					}
				}

				if (typeStr == null) {
					String colClassName = rsmd.getColumnClassName(i);
					typeStr = typeMapping.getType(colClassName);
				}

				if (typeStr == null) {
					int type = rsmd.getColumnType(i);
					if (type == Types.BINARY || type == Types.VARBINARY || type == Types.LONGVARBINARY || type == Types.BLOB) {
						typeStr = "byte[]";
					} else if (type == Types.CLOB || type == Types.NCLOB) {
						typeStr = "java.lang.String";
					}
					// 支持 oracle 的 TIMESTAMP、DATE 字段类型，其中 Types.DATE 值并不会出现
					// 保留对 Types.DATE 的判断，一是为了逻辑上的正确性、完备性，二是其它类型的数据库可能用得着
					else if (type == Types.TIMESTAMP || type == Types.DATE) {
						typeStr = "java.util.Date";
					}
					// 支持 PostgreSql 的 jsonb json
					else if (type == Types.OTHER) {
						typeStr = "java.lang.Object";
					} else {
						typeStr = "java.lang.String";
					}
				}
				cm.javaType = typeStr;

				// 构造字段对应的属性名 attrName
				cm.attrName = buildAttrName(cm.name);
				// 构造其他属性
//			buildColumnOtherAttr(tableMeta, cm, cm.name);

				tableMeta.columnMetas.add(cm);
			}
		}

		rs.close();
		stm.close();
	}

	protected void buildColumnOtherAttr(TableMeta tableMeta, ColumnMeta cm) throws SQLException {
		ResultSet rs = dbMeta.getColumns(conn.getCatalog(), null, tableMeta.name, cm.name);
		rs.next();
		// 长度
		int columnSize = rs.getInt("COLUMN_SIZE");
		if (columnSize > 0) {
			cm.type = cm.type + "(" + columnSize;
			// 小数位数
			int decimalDigits = rs.getInt("DECIMAL_DIGITS");
			if (decimalDigits > 0) {
				cm.type = cm.type + "," + decimalDigits;
			}
			cm.type = cm.type + ")";
		}

		// 是否允许 NULL 值
		cm.isNullable = rs.getString("IS_NULLABLE");
		if (cm.isNullable == null) {
			cm.isNullable = "";
		}

		cm.isPrimaryKey = "   ";
		String[] keys = tableMeta.primaryKey.split(",");
		for (String key : keys) {
			if (key.equalsIgnoreCase(cm.name)) {
				cm.isPrimaryKey = "PRI";
				break;
			}
		}

		// 默认值
		cm.defaultValue = rs.getString("COLUMN_DEF");
		if (cm.defaultValue == null) {
			cm.defaultValue = "";
		}

		// 备注
		cm.remarks = rs.getString("REMARKS");
		if (cm.remarks == null) {
			cm.remarks = "";
		}

		rs.close();
	}

	/**
	 * 构造 colName 所对应的 attrName，mysql 数据库建议使用小写字段名或者驼峰字段名
	 * Oralce 反射将得到大写字段名，所以不建议使用驼峰命名，建议使用下划线分隔单词命名法
	 */
	protected String buildAttrName(String colName) {
		return StrKit.toCamelCase(colName);
	}
}







