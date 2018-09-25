package com.renren.jinkong.kylin.dbtool.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 表格元数据实体类
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018-09-25
 */
public class TableMeta implements Serializable {

	private static final long serialVersionUID = -4659736956433755858L;
	/**
	 * 表名
	 */
	public String name;
	/**
	 * 表备注
	 */
	public String remarks;
	/**
	 * 主键，复合主键以逗号分隔
	 */
	public String primaryKey;
	/**
	 * 字段 meta
	 */
	public List<ColumnMeta> columnMetas = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public List<ColumnMeta> getColumnMetas() {
		return columnMetas;
	}

	public void setColumnMetas(List<ColumnMeta> columnMetas) {
		this.columnMetas = columnMetas;
	}

	@Override
	public String toString() {
		return "TableMeta{" +
				"name='" + name + '\'' +
				", remarks='" + remarks + '\'' +
				", primaryKey='" + primaryKey + '\'' +
				", columnMetas=" + columnMetas +
				'}';
	}
}