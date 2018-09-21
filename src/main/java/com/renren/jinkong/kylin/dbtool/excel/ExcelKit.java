package com.renren.jinkong.kylin.dbtool.excel;

import com.renren.jinkong.kylin.dbtool.kit.ReflectKit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/20 11:47
 */
class ExcelKit {

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
     * 数据表格表格到集合映射
     * 默认行为
     *
     * 第0行为表头行号
     * 第1行为数据起始行号
     * 最后一行为数据结束行号
     *
     * @return
     * @throws Exception
     */
    public List dataExcelMapToList() throws Exception {
        if(sheet == null) {
            setSheet();
        }

        ExcelMapper mapper = new ExcelMapper(sheet, 0, 1, sheet.getLastRowNum());

        // 获取所有数据
        List<Map<String, String>> dataListMap = mapper.getDataListMap();
        // 转换ListMap->ListObject
        return ReflectKit.transferToList(clazz, dataListMap);
    }

    /**
     * 数据表格表格到集合映射
     *
     * 指定表头行号，默认数据起始行号为表头行号+1
     * 数据结束行为最后一行
     *
     * @param headRowNum
     * @return
     * @throws Exception
     */
    public List dataExcelMapToList(int headRowNum) throws Exception {
        if(sheet == null) {
            setSheet();
        }

        ExcelMapper mapper = new ExcelMapper(sheet, headRowNum, headRowNum + 1, sheet.getLastRowNum());

        // 获取所有数据
        List<Map<String, String>> dataListMap = mapper.getDataListMap();
        // 转换ListMap->ListObject
        return ReflectKit.transferToList(clazz, dataListMap);
    }

    /**
     * 数据表格表格到集合映射
     *
     * 指定表头行号，默认数据起始行号为表头行号+1
     *
     * @param headRowNum 表头行号
     * @param endRowNum 数据的结束行号
     * @return
     * @throws Exception
     */
    public List dataExcelMapToList(int headRowNum, int endRowNum) throws Exception {
        if(sheet == null) {
            setSheet();
        }

        ExcelMapper mapper = new ExcelMapper(sheet, headRowNum, headRowNum + 1, endRowNum);

        // 获取所有数据
        List<Map<String, String>> dataListMap = mapper.getDataListMap();
        // 转换ListMap->ListObject
        return ReflectKit.transferToList(clazz, dataListMap);
    }

    /**
     * 数据表格表格到集合映射
     * 明确指定三个行号
     *
     * @param headRowNum 表头行号
     * @param startNum
     * @param endRowNum
     * @return
     * @throws Exception
     */
    public List dataExcelMapToList(int headRowNum, int startNum, int endRowNum) throws Exception {
        if(sheet == null) {
            setSheet();
        }

        ExcelMapper mapper = new ExcelMapper(sheet, headRowNum, startNum, endRowNum);

        // 获取所有数据
        List<Map<String, String>> dataListMap = mapper.getDataListMap();
        // 转换ListMap->ListObject
        return ReflectKit.transferToList(clazz, dataListMap);
    }


}
