package com.renren.jinkong.kylin.dbtool.excel;

import com.renren.jinkong.kylin.dbtool.core.executor.DefaultDataSourceExecutor;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 表格数据库泵
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/20 17:39
 */
public class ExcelDbPump {

    private File file;
    private Class clazz;
    private AtomicInteger headRowNum = new AtomicInteger(0);
    private AtomicInteger startRowNum = new AtomicInteger(1);
    private AtomicInteger endRowNum = new AtomicInteger(0);

    private DefaultDataSourceExecutor executor;

    public ExcelDbPump(String jdbcUrl, String user, String password) {
        this.executor = new DefaultDataSourceExecutor(jdbcUrl, user, password);
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public void set(File file, Class clazz) {
        this.file = file;
        this.clazz = clazz;
    }

    /**
     * 设置表头行号
     *
     * @param count
     */
    public void setHeadRowNum(int count) {
        this.headRowNum.set(count);
        this.startRowNum.set(count + 1);
    }

    /**
     * 设置数据起始行号
     *
     * @param endRowNum
     */
    public void setEndRowNum(int endRowNum) {
        this.endRowNum.set(endRowNum);
    }

    /**
     * 设置数据结束行号
     *
     * @param startRowNum
     */
    public void setStartRowNum(int startRowNum) {
        this.startRowNum.set(startRowNum);
    }

    public int execute() throws Exception {
        if(this.executor == null) {
            throw new IllegalArgumentException("DefaultDataSourceExecutor不能为空");
        }

        if(this.file == null) {
            throw new IllegalArgumentException("所需导入的File对象未设置");
        }

        if(this.clazz == null) {
            throw new IllegalArgumentException("表格所映射的实体未配置");
        }

        ExcelKit excelKit = new ExcelKit(file, clazz);

        List list = excelKit.dataExcelMapToList(this.headRowNum.get(), startRowNum.get(), endRowNum.get());

        return executor.batchInsert(clazz, list);
    }
}
