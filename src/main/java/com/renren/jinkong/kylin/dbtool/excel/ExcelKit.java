package com.renren.jinkong.kylin.dbtool.excel;

import com.renren.jinkong.kylin.dbtool.kit.ReflectKit;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
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
            this.sheet = setSheet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Sheet setSheet() throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        HSSFWorkbook workbook = new HSSFWorkbook(bis);

        // 读取第一个sheet
        Sheet sheet = workbook.getSheetAt(0);

        bis.close();

        return sheet;
    }

    public void setSheet(int row) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        HSSFWorkbook workbook = new HSSFWorkbook(bis);

        // 读取第一个sheet
        this.sheet = workbook.getSheetAt(row);

        bis.close();
    }

    public List dataExcelMapToBean() throws Exception {
        // 获取所有数据
        List<Map<String, String>> dataListMap = getDataListMap(getHeadMap());
        // 转换ListMap->ListObject
        return ReflectKit.transferToList(clazz, dataListMap);
    }

    public List dataExcelMapToBean(int startRowNum) throws Exception {
        // 获取所有数据
        List<Map<String, String>> dataListMap = getDataListMap(getHeadMap(startRowNum), startRowNum + 1);
        // 转换ListMap->ListObject
        return ReflectKit.transferToList(clazz, dataListMap);
    }

    private Map<String, Integer> getHeadMap() {
        return getHeadMap(0);
    }

    private Map<String, Integer> getHeadMap(int row) {
        Map<String, Integer> map = new HashMap<>();

        Row root = sheet.getRow(row);
        short lastCellNum = root.getLastCellNum();
        System.out.println(lastCellNum);

        for(int i = 0; i < root.getLastCellNum(); i++) {
            map.put(getCellValue(root.getCell(i)), i);
        }

        System.out.println(map);

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
     * @param headMap 头部map
     * @param rowNum 数据起始行号
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
                System.out.println("列名：" + cellName);
                String cellValue = getCellValue(row.getCell(head.getValue()));

                dataMap.put(cellName, cellValue);
            }

            dataListMap.add(dataMap);
        }

        return dataListMap;
    }


    private static String getCellValue(Cell cell) {
        System.out.println("类型：" + cell.getCellType());
        System.out.println("数值：" + cell);
        if (null == cell) {
            return "";
        }

        String cellValue = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
//                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//                cellValue = cell.toString();
                if(HSSFDateUtil.isCellDateFormatted(cell)){
                    //  如果是date类型则 ，获取该cell的date值
                    if(!"".equals(cell.getNumericCellValue())) {
                        cellValue = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
                    }
                } else {
                    // 纯数字
                    System.out.println("数字格式需要转化：" + cell);
                    DecimalFormat df = new DecimalFormat("#,##0.00");
                    cellValue = df.format(cell.getNumericCellValue()).replace(",", "");
                }

                break;
            case HSSFCell.CELL_TYPE_STRING:
                cellValue = cell.getRichStringCellValue().getString().trim();
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                break;

            default:
                cellValue = "";
        }

        return cellValue;
    }

}
