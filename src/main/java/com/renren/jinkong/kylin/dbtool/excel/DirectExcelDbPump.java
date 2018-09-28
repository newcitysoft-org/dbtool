package com.renren.jinkong.kylin.dbtool.excel;

import com.renren.jinkong.kylin.dbtool.core.executor.DirectDataSourceExecutor;
import com.renren.jinkong.kylin.dbtool.exception.ExcelDataNoMatchException;
import com.renren.jinkong.kylin.dbtool.kit.StrKit;
import com.renren.jinkong.kylin.dbtool.model.DbInfo;
import com.renren.jinkong.kylin.dbtool.model.DbOpDefinition;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * 直接方式的表格数据库泵
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

    private DbOpDefinition definition = DbOpDefinition.DEFAULT_DEFINITION;

    public DirectExcelDbPump(DbInfo info) {
        this.info = info;
        this.executor = new DirectDataSourceExecutor(info);
    }

    public DirectExcelDbPump(String jdbcUrl, String user, String password) {
        this.info = new DbInfo(jdbcUrl, user, password);
        this.executor = new DirectDataSourceExecutor(info);
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setDefinition(DbOpDefinition definition) {
        this.definition = definition;

        this.executor.setDtm(definition.getDtm());
        this.executor.setDbTableName(dbTableName);
        this.executor.setInMode(definition.getInMode());
    }

    public void setDbTableName(String dbTableName) {
        this.dbTableName = dbTableName;
        this.executor.setDbTableName(dbTableName);
    }

    /**
     * 批次号
     *
     * @param batchNo
     * @return
     * @throws Exception
     */
    public int execute(String batchNo) throws Exception {
        if(file == null) {
            throw new IllegalArgumentException("所需导入的File对象未设置");
        }

        if(StrKit.isBlank(dbTableName)) {
            throw new IllegalArgumentException("未指定所导入的数据");
        }

        ExcelKit excelKit = new ExcelKit(file);
        // 提前设置表格版本
        excelKit.setVersion(definition.getVersion());
        // 设置sheet页
        if(!StrKit.isBlank(definition.getSheetName())) {
            excelKit.setSheet(definition.getSheetName());
        }

        // 校验
        List<String> heads = excelKit.getSheetHeads(definition.getHeadRowNum());
        List<String> tableFields = executor.getTableFields();

        if(!fieldCheck(heads, tableFields)) {
            throw new ExcelDataNoMatchException("表格与数据库字段不完全匹配！");
        }

        List list = excelKit.getExcelDataMap(definition.getHeadRowNum(),
                definition.getStartRowNum(),
                definition.getEndRowNum());

        return executor.batchInsert(list, definition.getDayOrMonth(), batchNo);
    }

    /**
     * 字段检测
     *
     * 若数据库中字段完全包含表格的字段 为 true
     *
     * @param heads
     * @param tableFields
     * @return
     */
    private boolean fieldCheck(List<String> heads, List<String> tableFields) {
        int count = 0;

        for(String head : heads) {
            if(tableFields.contains(head)) {
                count++;
            }
        }

        if(count == heads.size()) {
            return true;
        }

        return false;
    }
}
