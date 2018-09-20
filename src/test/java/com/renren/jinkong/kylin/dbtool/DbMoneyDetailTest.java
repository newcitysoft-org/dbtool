package com.renren.jinkong.kylin.dbtool;

import com.renren.jinkong.kylin.dbtool.core.executor.DefaultDataSourceExecutor;
import com.renren.jinkong.kylin.dbtool.excel.ExcelKit;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/20 13:24
 */
public class DbMoneyDetailTest {

    private static final String url = "jdbc:mysql://localhost:3306/springboot_demo?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
    private static final String user = "root";
    private static final String password = "root";

    @Test
    public void testAdd() throws Exception {
        File file = new File("D:\\data\\01.xls");
        ExcelKit excelKit = new ExcelKit(file, MoneyDetail.class);
        DefaultDataSourceExecutor executor = new DefaultDataSourceExecutor(url, user, password);

        excelKit.setSheet("Sheet1");

        List list = excelKit.dataExcelMapToBean();
        int i = executor.batchInsert(MoneyDetail.class, list);
        System.out.println(i);
    }

    @Test
    public void testAdd2() throws Exception {
        File file = new File("车商资本金明细.xls");
        ExcelKit excelKit = new ExcelKit(file, MoneyDetail.class);
        DefaultDataSourceExecutor executor = new DefaultDataSourceExecutor(url, user, password);

        List list = excelKit.dataExcelMapToBean(1);
        int i = executor.batchInsert(MoneyDetail.class, list);
        System.out.println(i);
    }

    @Test
    public void testSheet() throws Exception {
        File file = new File("车商资本金明细.xls");
        ExcelKit excelKit = new ExcelKit(file, MoneyDetail.class);

        excelKit.setSheet("车商资本金明细");


        Sheet sheet = excelKit.getSheet();
        System.out.println(sheet);
    }

    @Test
    public void testHss() throws Exception {
        File file = new File("车商资本金明细.xls");
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        HSSFWorkbook workbook = new HSSFWorkbook(bis);

        int numberOfSheets = workbook.getNumberOfSheets();

        for (int i = 0; i < numberOfSheets; i++) {
            String sheetName = workbook.getSheetName(i);
            System.out.println(sheetName);
            System.out.println("表头：" + getHeadMap(workbook.getSheetAt(i), 0));
        }

        System.out.println(numberOfSheets);
    }

    @Test
    public void testHss2() throws Exception {
        File file = new File("车商资本金明细.xls");
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        HSSFWorkbook workbook = new HSSFWorkbook(bis);

        int activeSheetIndex = workbook.getActiveSheetIndex();
        HSSFSheet sheetAt = workbook.getSheetAt(activeSheetIndex);

        System.out.println(sheetAt.getSheetName());
        System.out.println("表头：" + getHeadMap(sheetAt, 1));
    }

    private Map<String, Integer> getHeadMap(Sheet sheet, int row) {
        Map<String, Integer> map = new HashMap<>();

        Row root = sheet.getRow(row);

        short lastCellNum = root.getLastCellNum();

        for(int i = 0; i < lastCellNum; i++) {
            map.put(ExcelKit.getCellValue(root.getCell(i)), i);
        }

        return map;
    }

}
