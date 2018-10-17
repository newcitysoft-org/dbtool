package com.renren.jinkong.kylin.dbtool.excel;

import com.renren.jinkong.kylin.dbtool.core.executor.DirectDataSourceExecutor;
import com.renren.jinkong.kylin.dbtool.exception.ExcelDataNoMatchException;
import com.renren.jinkong.kylin.dbtool.exception.ExcelVersionException;
import com.renren.jinkong.kylin.dbtool.kit.StrKit;
import com.renren.jinkong.kylin.dbtool.model.DbInfo;
import com.renren.jinkong.kylin.dbtool.model.DbOpDefinition;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

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

    /**
     * 数据操作定义
     */
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
     * 开启水泵
     * 执行插入数据
     *
     * @param batchNo 批次号
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

        try {
            // 校验
            List<String> heads = excelKit.getSheetHeads(definition.getHeadRowNum());
            List<String> tableFields = executor.getTableFields();
            // 字段检测，检查数据库中的字段是否全部包含表格的表头
            if(!fieldCheck(heads, tableFields)) {
                throw new ExcelDataNoMatchException("表格与数据库字段不完全匹配！数据库字段：" + tableFields);
            }
            // 获取表格数据
            List list = excelKit.getExcelDataMap(definition.getHeadRowNum(),
                    definition.getStartRowNum(),
                    definition.getEndRowNum());
            // 执行批量插入操作
            return executor.batchInsert(list, definition.getDayOrMonth(), batchNo);
        } catch (OfficeXmlFileException e) {
            throw new ExcelVersionException("该版本表格不支持，请选择97-2003的xls文件。");
        } catch (RuntimeException e) {
            if(e.getMessage().contains("InvalidFormatException")) {
                throw new ExcelVersionException("该版本表格不支持，请选择2007的xlsx文件。");
            }

            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 字段检测
     *
     * 若数据库中字段完全包含表格的字段 为 true
     *
     * @param heads 表格表头集合
     * @param tableFields 数据字段集合
     * @return
     */
    private boolean fieldCheck(List<String> heads, List<String> tableFields) {
        int count = 0;
        // 去除集合中字符串前后空格
        List<String> trims = StrKit.trims(tableFields);

        for(String head : heads) {
            if(trims.contains(head.trim())) {
                count++;
            }
        }

        if(count == heads.size()) {
            return true;
        }

        return false;
    }
}
