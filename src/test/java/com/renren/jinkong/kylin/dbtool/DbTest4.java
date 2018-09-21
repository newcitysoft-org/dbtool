//package com.renren.jinkong.kylin.dbtool;
//
//import com.renren.jinkong.kylin.dbtool.core.executor.DefaultDataSourceExecutor;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author lixin.tian@renren-inc.com
// * @date 2018/9/19 17:23
// */
//public class DbTest4 {
//
//    private static final String url = "jdbc:mysql://localhost:3306/springboot_demo?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
//    private static final String user = "root";
//    private static final String password = "root";
//
//    @Test
//    public void testAdd() {
//        DefaultDataSourceExecutor executor = new DefaultDataSourceExecutor(url, user, password);
//
//        List<Student> list = new ArrayList<>();
//
//        Student student = new Student();
//
//        student.setName("tianlixin");
//        student.setStuNo("asda");
//        student.setAddress("asdasd");
//
//        list.add(student);
//
//        int i = executor.batchInsert(Student.class, list);
//
//        System.out.println(i);
//    }
//
//    @Test
//    public void testUpdate() {
//        DefaultDataSourceExecutor executor = new DefaultDataSourceExecutor(url, user, password);
//
//        List<Student> list = new ArrayList<>();
//
//        Student student = new Student();
//
//        student.setId(18);
//        student.setName("tianlixin");
//        student.setStuNo("111");
//        student.setAddress("414141");
//
//        list.add(student);
//
//        int i = executor.batchUpdate(Student.class, list);
//
//        System.out.println(i);
//    }
//
//    @Test
//    public void testDelete() {
//        DefaultDataSourceExecutor executor = new DefaultDataSourceExecutor(url, user, password);
//
//        List list = Arrays.asList(5,6,7,8);
//
//        int i = executor.batchDelete(Student.class, list);
//
//        System.out.println(i);
//    }
//}
