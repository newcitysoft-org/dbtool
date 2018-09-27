package com.renren.jinkong.kylin.dbtool.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/25 16:57
 */
public final class ExcelUtil {
    private ExcelUtil() {}

    public static List<String> getAllSheetName(File file) throws IOException {
        List<String> list = new ArrayList<String>();

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        HSSFWorkbook workbook = new HSSFWorkbook(bis);

        int numberOfSheets = workbook.getNumberOfSheets();

        for (int i = 0; i < numberOfSheets; i++) {
            String sheetName = workbook.getSheetAt(i).getSheetName();

            list.add(sheetName);
        }

        bis.close();

        return list;
    }


    public static Map<String, Integer> getAllSheetNameIndexMap(File file) throws IOException {
        Map<String, Integer> map = new HashMap<String, Integer>();

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        HSSFWorkbook workbook = new HSSFWorkbook(bis);

        int numberOfSheets = workbook.getNumberOfSheets();

        for (int i = 0; i < numberOfSheets; i++) {
            String sheetName = workbook.getSheetAt(i).getSheetName();

            map.put(sheetName, i);
        }

        bis.close();

        return map;
    }
}
