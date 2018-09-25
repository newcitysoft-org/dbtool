package com.renren.jinkong.kylin.dbtool.excel;

import com.renren.jinkong.kylin.dbtool.kit.StrKit;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 表格映射工具
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/20 11:47
 */
public class ExcelMapper {

    private Sheet sheet;
    private int headRowNum;
    private int startRowNum;
    private int endRowNum;

    public ExcelMapper(Sheet sheet, int headRowNum, int startRowNum, int endRowNum) {
        this.sheet = sheet;
        this.headRowNum = headRowNum;
        this.startRowNum = startRowNum;
        this.endRowNum = endRowNum;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
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

    /**
     * 指定头部信息的起始行号
     *
     * @return
     */
    private Map<String, Integer> getHeadMap() {
        Row root = sheet.getRow(headRowNum);
        short lastCellNum = root.getLastCellNum();

        Map<String, Integer> map = new HashMap<>(lastCellNum);

        for(int i = 0; i < lastCellNum; i++) {
            String title = getCellValue(root.getCell(i));

            if(title != null && !StrKit.isBlank(title)) {
                map.put(title, i);
            }
        }

        return map;
    }

    /**
     * 指定读取数据的起始行号和结束行号
     *
     * @return
     */
    public List<Map<String, String>> getDataListMap() {
        Map<String, Integer> headMap = getHeadMap();
        List<Map<String, String>> dataListMap = new LinkedList<>();
        if(endRowNum == 0) {
            endRowNum = sheet.getLastRowNum();
        }

        // 遍历所有行
        for (int i = startRowNum; i <= endRowNum; i++) {
            Row row = sheet.getRow(i);
            // 判断是否为空行
            if(!isRowEmpty(row)) {
                Map<String, String> dataMap = new HashMap<>(headMap.size());
                // 获取每行对应的数据
                for(Map.Entry<String, Integer> head : headMap.entrySet()) {
                    String cellName = head.getKey();
                    String cellValue = getCellValue(row.getCell(head.getValue()));
                    // 添加数据
                    dataMap.put(cellName, cellValue);
                }

                dataListMap.add(dataMap);
            }
        }

        return dataListMap;
    }

    /**
     * 判断行是否为空行
     *
     * @param row
     * @return
     */
    public static boolean isRowEmpty(Row row) {
        if(row == null) {
            return true;
        }

        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);

            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }

        return true;
    }

    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        if (null == cell) {
            return "";
        }

        String cellValue = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
                if(HSSFDateUtil.isCellDateFormatted(cell)){
                    //  如果是date类型则 ，获取该cell的date值
                    if(!"".equals(cell.getNumericCellValue())) {
                        cellValue = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
                    }
                } else {
                    // 纯数字
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