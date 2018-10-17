package com.renren.jinkong.kylin.dbtool.excel;

import com.renren.jinkong.kylin.dbtool.core.op.ExcelVersion;
import com.renren.jinkong.kylin.dbtool.exception.ExcelVersionException;
import com.renren.jinkong.kylin.dbtool.kit.ReflectKit;
import com.renren.jinkong.kylin.dbtool.model.Kv;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
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
    private ExcelVersion version;

    public ExcelKit(File file) {
        this.file = file;
    }

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

    public void setVersion(ExcelVersion version) {
        this.version = version;
    }

    private Workbook createWorkbook() throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        Workbook workbook;

        switch (version) {
            case VERSION_2003:
                workbook = new HSSFWorkbook(bis);
                break;
            case VERSION_2007:
                workbook = new XSSFWorkbook(bis);
                break;
            default:
                workbook = new XSSFWorkbook(bis);
                break;
        }

        bis.close();

        return workbook;
    }

    /**
     * 默认设置sheet页
     *
     * @return
     * @throws Exception
     */
    private void setSheet() throws Exception {
        Workbook workbook = createWorkbook();

        int activeSheetIndex = workbook.getActiveSheetIndex();
        // 读取活动的sheet页
        this.sheet = workbook.getSheetAt(activeSheetIndex);


    }

    /**
     * 根据sheet页名称设置
     *
     * @param sheetName
     * @throws Exception
     */
    public void setSheet(String sheetName) throws Exception {
        Workbook workbook = createWorkbook();
        // 根据指定的sheet名获取sheet页
        this.sheet = workbook.getSheet(sheetName);
    }

    /**
     * 根据编号设置
     *
     * @param row
     * @throws Exception
     */
    public void setSheet(int row) throws Exception {
        Workbook workbook = createWorkbook();
        // 读取第一个sheet
        this.sheet = workbook.getSheetAt(row);
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

    /**
     * 根据各行号获取表格数据映射集合
     *
     * @param headRowNum 表头行号，从0开始
     * @param startNum 数据起始行号，从0开始
     * @param endRowNum 数据结束行号，从0开始
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> getExcelDataMap(int headRowNum, int startNum, int endRowNum) throws Exception {
        if(sheet == null) {
            setSheet();
        }

        ExcelMapper mapper = new ExcelMapper(sheet, headRowNum, startNum, endRowNum);

        // 获取所有数据
        List<Map<String, String>> dataListMap = mapper.getDataListMap();
        return dataListMap;
    }

    /**
     * 获取所有sheet页名称与索引的映射集合
     *
     * @return
     * @throws Exception
     */
    public List<Kv<String, Integer>> getSheetNames() throws Exception {
        List<Kv<String, Integer>> list = new ArrayList<Kv<String, Integer>>();

        try {
            Workbook workbook = createWorkbook();

            int numberOfSheets = workbook.getNumberOfSheets();

            for (int i = 0; i < numberOfSheets; i++) {
                list.add(new Kv<String, Integer>(workbook.getSheetAt(i).getSheetName(), i));
            }
        } catch (OfficeXmlFileException e) {
            throw new ExcelVersionException("该版本表格不支持，请选择97-2003的xls文件。");
        } catch (RuntimeException e) {
            if(e.getMessage().contains("InvalidFormatException")) {
                throw new ExcelVersionException("该版本表格不支持，请选择2007的xlsx文件。");
            }

            throw new RuntimeException(e);
        }

        return list;
    }

    /**
     * 获取表格当前sheet页中的所有表头
     *
     * @param headRowNum
     * @return
     * @throws Exception
     */
    public List<String> getSheetHeads(int headRowNum) throws Exception {
        if(sheet == null) {
            setSheet();
        }

        ExcelMapper mapper = new ExcelMapper(sheet, headRowNum);

        Map<String, Integer> headMap = mapper.getHeadMap();

        return new LinkedList<String>(headMap.keySet());
    }
}
