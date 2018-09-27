package com.renren.jinkong.kylin.dbtool.model;

import com.renren.jinkong.kylin.dbtool.core.op.DbInMode;
import com.renren.jinkong.kylin.dbtool.core.op.DbTimeDimension;
import com.renren.jinkong.kylin.dbtool.core.op.ExcelType;
import com.renren.jinkong.kylin.dbtool.kit.DateKit;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/26 13:58
 */
public class DbOpDefinition {

    public static final DbOpDefinition DEFAULT_DEFINITION = new DbOpDefinition();

    static {
        DEFAULT_DEFINITION.sheetName = "";
        DEFAULT_DEFINITION.headRowNum = 0;
        DEFAULT_DEFINITION.startRowNum = 1;
        DEFAULT_DEFINITION.endRowNum = 0;
        DEFAULT_DEFINITION.inMode = DbInMode.ADD;
        DEFAULT_DEFINITION.excelType = ExcelType.ALL;
    }

    /**
     * 数据时间
     */
    private Object dayOrMonth;
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
    private int startRowNum;
    /**
     * 数据结束行号
     */
    private int endRowNum;

    private ExcelType excelType;

    private DbTimeDimension dtm;

    private DbInMode inMode;

    public DbOpDefinition() {
    }

    public Object getDayOrMonth() {
        return dayOrMonth;
    }

    public void setDayOrMonth(Object dayOrMonth) {

        if(dtm == DbTimeDimension.DAY && dayOrMonth instanceof String) {
            String date = (String) dayOrMonth;

            if(DateKit.validate(date)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    dayOrMonth = sdf.parse(date);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("日期类型不符合如下格式：yyyy-MM-dd；" +
                            "需自己转化日期格式，或直接设置Date类型。");
                }
            }
        }

        this.dayOrMonth = dayOrMonth;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getHeadRowNum() {
        return headRowNum;
    }

    public void setHeadRowNum(int headRowNum) {
        this.headRowNum = headRowNum;
    }

    public int getStartRowNum() {
        return startRowNum;
    }

    public void setStartRowNum(int startRowNum) {
        this.startRowNum = startRowNum;
    }

    public int getEndRowNum() {
        return endRowNum;
    }

    public void setEndRowNum(int endRowNum) {
        this.endRowNum = endRowNum;
    }

    public ExcelType getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelType excelType) {
        this.excelType = excelType;

        switch (excelType) {
            case MONTH:
                this.inMode = DbInMode.DELETE_DATE_AND_ADD;
                this.dtm = DbTimeDimension.MONTH;
                break;
            case DAY:
                this.inMode = DbInMode.DELETE_DATE_AND_ADD;
                this.dtm = DbTimeDimension.DAY;
                break;
            default:
                break;
        }
    }

    public DbTimeDimension getDtm() {
        return dtm;
    }

    public void setDtm(DbTimeDimension dtm) {
        this.dtm = dtm;
    }

    public DbInMode getInMode() {
        return inMode;
    }

    public void setInMode(DbInMode inMode) {
        this.inMode = inMode;
    }
}
