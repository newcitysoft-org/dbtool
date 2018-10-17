package com.renren.jinkong.kylin.dbtool.core.executor;

import com.renren.jinkong.kylin.dbtool.core.Const;
import com.renren.jinkong.kylin.dbtool.core.DirectSqlGenerator;
import com.renren.jinkong.kylin.dbtool.core.TypeMapping;
import com.renren.jinkong.kylin.dbtool.core.op.DbInMode;
import com.renren.jinkong.kylin.dbtool.core.op.DbTimeDimension;
import com.renren.jinkong.kylin.dbtool.core.op.SqlOperation;
import com.renren.jinkong.kylin.dbtool.model.ColumnMeta;
import com.renren.jinkong.kylin.dbtool.model.TableMeta;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/25 13:50
 */
public class DirectMysqlDbExecutor {

    private DataSource source;
    private TableMeta tableMeta;
    private DbInMode dbInMode;
    private DbTimeDimension dtm;

    public DirectMysqlDbExecutor(DataSource source, TableMeta tableMeta, DbInMode inMode, DbTimeDimension dtm) {
        this.source = source;
        this.tableMeta = tableMeta;
        this.dbInMode = inMode;
        this.dtm = dtm;
    }

    public int batchInsert(List<Map<String, String>> list, Object dateOrMonth, String batchNo) {
        int rows = 0;
        // 是否提前删除数据
        // 模式一：提前删除所有数据
        if(this.dbInMode == DbInMode.DELETE_AND_ADD) {
            fakeDelete(tableMeta, batchNo);
        } else if(this.dbInMode == DbInMode.DELETE_DATE_AND_ADD) {
            if(this.dtm == null) {
                throw new IllegalArgumentException("所删除维度未选择！");
            }

            if(dateOrMonth == null) {
                throw new IllegalArgumentException("维度所对应的时间未设置！");
            }

            // 模式二：提前删除各维度的所有数据
            switch (this.dtm) {
                case DAY:
                    // 按日维度删除数据
                    fakeDeleteByDay(tableMeta, dateOrMonth, batchNo);
                    break;
                case MONTH:
                    // 按月维度删除数据
                    fakeDeleteByMonth(tableMeta, dateOrMonth, batchNo);
                    break;
                default:
                    break;
            }
        }

        // 添加数据
        String sql = DirectSqlGenerator.generateSql(tableMeta);

        for (Map<String, String> map : list) {
            int row = insert(sql, map, dateOrMonth, batchNo);
            rows += row;
        }

        return rows;
    }


    public int insert(String sql, final Map<String, String> map, final Object dateOrMonth, final String batchNo) {
        int row = new SqlOperation(source, sql) {

            @Override
            public void push(PreparedStatement ps) {
                // 按照SQL生成器的字段顺序设置值
                List<ColumnMeta> columnMetas = tableMeta.columnMetas;

                if(columnMetas != null && columnMetas.size() > 0) {
                    for (int i = 0; i < columnMetas.size(); i++) {
                        ColumnMeta meta = columnMetas.get(i);
                        Object o = null;
                        // 获取字段名
                        String name = meta.getName();
                        // 操作时间赋值
                        if(Const.FIELD_RECORD_DAY.equals(name) || Const.FIELD_RECORD_MONTH.equals(name)) {
                            o = dateOrMonth;
                        } else if(Const.FIELD_RECORD_STATE.equals(name)) {
                            o = 1;
                        } else if(Const.FIELD_RECORD_BATCH_NO.equals(name)) {
                            o = batchNo;
                        } else{
                            // 获取字段备注
                            String remarks = meta.getRemarks().trim();
                            // 强映射获取数值
                            String value = map.get(remarks);
                            // 设置值
                            o = TypeMapping.convertType(meta.getJavaType(), value);
                        }

                        try {
                            ps.setObject(i + 1, o);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.execute();

        return row;
    }

//    public int delete(TableMeta tableMeta) {
//        String sql = "delete  from " + tableMeta.getName();
//
//        int row = new SqlOperation(source, sql) {
//
//            @Override
//            public void push(PreparedStatement ps) {
//
//            }
//        }.execute();
//
//        return row;
//    }

    public int fakeDelete(TableMeta tableMeta, final String timestamp) {
        String sql = "update " + tableMeta.getName() + " set record_state=?,record_batch_no=?";

        int row = new SqlOperation(source, sql) {

            @Override
            public void push(PreparedStatement ps) {
                try {
                    ps.setObject(1, 0);
                    ps.setObject(2, timestamp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.execute();

        return row;
    }

    public int fakeDeleteByMonth(TableMeta tableMeta, final Object month, final String timestamp) {
        String sql = "update " + tableMeta.getName() + " set record_state=?,record_batch_no=? where record_month=?";

        int row = new SqlOperation(source, sql) {

            @Override
            public void push(PreparedStatement ps) {
                try {
                    ps.setObject(1, 0);
                    ps.setObject(2, timestamp);
                    ps.setObject(3, month);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.execute();

        return row;
    }

//    public int deleteByMonth(TableMeta tableMeta, Object month) {
//        String sql = "delete  from " + tableMeta.getName() + " where record_month=?";
//
//        int row = new SqlOperation(source, sql) {
//
//            @Override
//            public void push(PreparedStatement ps) {
//                try {
//                    ps.setObject(1, month);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.execute();
//
//        return row;
//    }

//    @Deprecated
//    public int deleteByDay(TableMeta tableMeta, Object day) {
//        String sql = "delete  from " + tableMeta.getName() + " where record_dt=?";
//
//        int row = new SqlOperation(source, sql) {
//
//            @Override
//            public void push(PreparedStatement ps) {
//                try {
//                    ps.setObject(1, day);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.execute();
//
//        return row;
//    }

    public int fakeDeleteByDay(TableMeta tableMeta, final Object day, final String timestamp) {
        String sql = "update " + tableMeta.getName() + " set record_state=?,record_batch_no=? where record_dt=?";

        int row = new SqlOperation(source, sql) {

            @Override
            public void push(PreparedStatement ps) {
                try {
                    ps.setObject(1, 0);
                    ps.setObject(2, timestamp);
                    ps.setObject(3, day);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }.execute();

        return row;
    }
}
