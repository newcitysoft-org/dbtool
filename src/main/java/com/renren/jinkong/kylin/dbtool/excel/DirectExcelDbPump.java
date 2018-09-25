package com.renren.jinkong.kylin.dbtool.excel;

import com.renren.jinkong.kylin.dbtool.core.executor.DirectDataSourceExecutor;
import com.renren.jinkong.kylin.dbtool.core.op.DbInMode;
import com.renren.jinkong.kylin.dbtool.kit.StrKit;
import com.renren.jinkong.kylin.dbtool.model.DbInfo;

import java.io.File;
import java.util.List;

/**
 * 表格数据库泵
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/20 17:39
 */
public class DirectExcelDbPump {
    /**
     * 直接方式执行器对象（带数据源）
     */
    private DirectDataSourceExecutor executor;
    /**
     * 文件对象
     */
    private File file;
    /**
     * 数据库信息
     */
    private DbInfo info;
    /**
     * 表格名
     */
    private String dbTableName;
    /**
     * 插入模式
     */
    private DbInMode inMode = DbInMode.ADD;
    /**
     * sheet页名称
     */
    private String sheetName;
    /**
     * 表头行号
     */
    private int headRowNum;
    /**
     * 数据开始行号
     */
    private int startRowNum = 1;
    /**
     * 数据结束行号
     */
    private int endRowNum;

    public DirectExcelDbPump(DbInfo info) {
        this.info = info;
    }

    public DirectExcelDbPump(String jdbcUrl, String user, String password) {
        this.info = new DbInfo(jdbcUrl, user, password);
    }



    /**
     * 设置插入模式
     *
     * @param inMode
     */
    public void setInMode(DbInMode inMode) {
        this.inMode = inMode;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setDbTableName(String dbTableName) {
        this.dbTableName = dbTableName;
        // 创建直接方式的执行器对象
        this.executor = new DirectDataSourceExecutor(info, dbTableName, inMode);
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * 设置表头行号
     *
     * @param count
     */
    public void setHeadRowNum(int count) {
        this.headRowNum = count;
        this.startRowNum = count + 1;
    }

    /**
     * 设置数据起始行号
     *
     * @param endRowNum
     */
    public void setEndRowNum(int endRowNum) {
        this.endRowNum = endRowNum;
    }

    /**
     * 设置数据结束行号
     *
     * @param startRowNum
     */
    public void setStartRowNum(int startRowNum) {
        this.startRowNum = startRowNum;
    }

    public int execute() throws Exception {
        if(this.file == null) {
            throw new IllegalArgumentException("所需导入的File对象未设置");
        }

        if(StrKit.isBlank(dbTableName)) {
            throw new IllegalArgumentException("未指定所导入的数据");
        }

        ExcelKit excelKit = new ExcelKit(file);

        if(!StrKit.isBlank(this.sheetName)) {
            excelKit.setSheet(this.sheetName);
        }

        List list = excelKit.getExcelDataMap(this.headRowNum, startRowNum, endRowNum);

        System.out.println(list);

        return executor.batchInsert(list);
    }
}
