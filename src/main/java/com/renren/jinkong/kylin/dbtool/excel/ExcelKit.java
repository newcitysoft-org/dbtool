package com.renren.jinkong.kylin.dbtool.excel;

import com.renren.jinkong.kylin.dbtool.kit.ReflectKit;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/20 11:47
 */
public class ExcelKit {

    private Class clazz;
    private File file;
    private Sheet sheet;

    public ExcelKit(File file, Class clazz) {
        this.file = file;
        this.clazz = clazz;

        try {
            this.sheet = getSheet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Sheet getSheet() throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        HSSFWorkbook workbook = new HSSFWorkbook(bis);

        // 读取第一个sheet
        Sheet sheet = workbook.getSheetAt(0);

        bis.close();

        return sheet;
    }

    public List dataExcelMapToBean() throws Exception {
        // 获取所有数据
        List<Map<String, String>> dataListMap = getDataListMap(getHeadMap());
        // 转换ListMap->ListObject
        return ReflectKit.transferToList(clazz, dataListMap);
    }

    private Map<String, Integer> getHeadMap() {
        return getHeadMap(0);
    }

    private Map<String, Integer> getHeadMap(int row) {
        Map<String, Integer> map = new HashMap<>();

        Row root = sheet.getRow(row);

        for(int i = 0; i < root.getLastCellNum(); i++) {
            map.put(getCellValue(root.getCell(i)), i);
        }

        return map;
    }

    /**
     * 从表格中获取ListMap数据
     * 从第第二行开始
     *
     * @param headMap
     * @return
     */
    private List<Map<String, String>> getDataListMap(Map<String, Integer> headMap) {
        return getDataListMap(headMap, 1);
    }

    /**
     * 从表格中获取ListMap数据
     *
     * @param headMap
     * @param rowNum
     * @return
     */
    private List<Map<String, String>> getDataListMap(Map<String, Integer> headMap, int rowNum) {
        List<Map<String, String>> dataListMap = new LinkedList<>();

        int lastRowNum = sheet.getLastRowNum();

        for (int i = rowNum; i <= lastRowNum; i++) {
            Map<String, String> dataMap = new HashMap<>();
            Row row = sheet.getRow(i);

            for(Map.Entry<String, Integer> head : headMap.entrySet()) {
                String cellName = head.getKey();
                String cellValue = getCellValue(row.getCell(head.getValue()));

                dataMap.put(cellName, cellValue);
            }

            dataListMap.add(dataMap);
        }

        return dataListMap;
    }


    private static String getCellValue(Cell cell) {
        if (null == cell) {
            return "";
        }

        String cellValue = "";
        DecimalFormat df = new DecimalFormat("#");
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                cellValue = cell.getRichStringCellValue().getString().trim();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                cellValue = df.format(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula();
                break;
            default:
                cellValue = "";
        }

        return cellValue;
    }

}
