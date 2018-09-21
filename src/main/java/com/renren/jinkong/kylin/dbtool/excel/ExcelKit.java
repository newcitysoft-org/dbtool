package com.renren.jinkong.kylin.dbtool.excel;

import com.renren.jinkong.kylin.dbtool.kit.ReflectKit;
import com.renren.jinkong.kylin.dbtool.kit.StrKit;
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
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Sheet getSheet() {
        return this.sheet;
    }

    /**
     * 默认设置sheet页
     *
     * @return
     * @throws Exception
     */
    private void setSheet() throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        HSSFWorkbook workbook = new HSSFWorkbook(bis);

        int activeSheetIndex = workbook.getActiveSheetIndex();
        // 读取活动的sheet页
        this.sheet = workbook.getSheetAt(activeSheetIndex);

        bis.close();
    }

    /**
     * 根据sheet页名称设置
     *
     * @param sheetName
     * @throws Exception
     */
    public void setSheet(String sheetName) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        HSSFWorkbook workbook = new HSSFWorkbook(bis);
        // 根据指定的sheet名获取sheet页
        this.sheet = workbook.getSheet(sheetName);

        bis.close();
    }

    /**
     * 根据编号设置
     *
     * @param row
     * @throws Exception
     */
    public void setSheet(int row) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        HSSFWorkbook workbook = new HSSFWorkbook(bis);

        // 读取第一个sheet
        this.sheet = workbook.getSheetAt(row);

        bis.close();
    }

    /**
     * 数据表格映射到javabean
     *
     * @return
     * @throws Exception
     */
    public List dataExcelMapToBean() throws Exception {
        // 获取所有数据
        List<Map<String, String>> dataListMap = getDataListMap(getHeadMap());
        // 转换ListMap->ListObject
        return ReflectKit.transferToList(clazz, dataListMap);
    }

    /**
     * 数据表格映射到javabean
     * 指定起始行号
     *
     * @param headRowNum
     * @return
     * @throws Exception
     */
    public List dataExcelMapToBean(int headRowNum) throws Exception {
        // 获取所有数据
        List<Map<String, String>> dataListMap = getDataListMap(getHeadMap(headRowNum), headRowNum + 1);
        // 转换ListMap->ListObject
        return ReflectKit.transferToList(clazz, dataListMap);
    }

    /**
     *
     *
     * @param headRowNum 表头的起始行号
     * @param endRowNum 数据的结束行号
     * @return
     * @throws Exception
     */
    public List dataExcelMapToBean(int headRowNum, int endRowNum) throws Exception {
        // 获取所有数据
        List<Map<String, String>> dataListMap = getDataListMap(getHeadMap(headRowNum), headRowNum + 1, endRowNum);
        // 转换ListMap->ListObject
        return ReflectKit.transferToList(clazz, dataListMap);
    }

    /**
     * 默认指定首行为标题行
     *
     * @return
     */
    private Map<String, Integer> getHeadMap() {
        return getHeadMap(0);
    }

    /**
     * 指定头部信息的起始行号
     *
     * @param headRowNum
     * @return
     */
    private Map<String, Integer> getHeadMap(int headRowNum) {
        if(this.sheet == null) {
            try {
                setSheet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
     * 从表格中获取ListMap数据
     * 从第二行开始
     *
     * @param headMap
     * @return
     */
    private List<Map<String, String>> getDataListMap(Map<String, Integer> headMap) {
        return getDataListMap(headMap, 1);
    }

    /**
     * 从表格中获取ListMap数据
     * 到末尾行结束
     *
     * @param headMap 头部map
     * @param startRowNum 数据起始行号
     * @return
     */
    private List<Map<String, String>> getDataListMap(Map<String, Integer> headMap, int startRowNum) {
        return getDataListMap(headMap, startRowNum, sheet.getLastRowNum());
    }

    /**
     * 指定读取数据的起始行号和结束行号
     *
     * @param headMap
     * @param startRowNum
     * @param endRowNum
     * @return
     */
    private List<Map<String, String>> getDataListMap(Map<String, Integer> headMap, int startRowNum, int endRowNum) {
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
