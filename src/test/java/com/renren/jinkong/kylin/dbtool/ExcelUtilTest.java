//package com.renren.jinkong.kylin.dbtool;
//
//import com.renren.jinkong.kylin.dbtool.util.ExcelUtil;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author lixin.tian@renren-inc.com
// * @date 2018/9/25 17:05
// */
//public class ExcelUtilTest {
//
//    @Test
//    public void getAllSheetNameTest() throws IOException {
//        File file = new File("D:\\data\\各城市再投入明细修改版2.xls");
//
//        List<String> allSheetName = ExcelUtil.getAllSheetName(file);
//        System.out.println(allSheetName);
//    }
//
//    @Test
//    public void getAllSheetNameIndexMapTest() throws IOException {
//        File file = new File("D:\\data\\各城市再投入明细修改版2.xls");
//
//        Map<String, Integer> allSheetName = ExcelUtil.getAllSheetNameIndexMap(file);
//        System.out.println(allSheetName);
//    }
//}
