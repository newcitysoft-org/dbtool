//package com.renren.jinkong.kylin.dbtool;
//
//import com.renren.jinkong.kylin.dbtool.core.manager.DataSourceManager;
//import com.renren.jinkong.kylin.dbtool.core.executor.DbExecutor;
//import com.renren.jinkong.kylin.dbtool.core.executor.DbExecutorFactory;
//import com.renren.jinkong.kylin.dbtool.core.DbType;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author lixin.tian@renren-inc.com
// * @date 2018/9/19 14:18
// */
//public class DbTest {
//
//    private static final String url = "jdbc:mysql://localhost:3306/springboot_demo?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
//    private static final String user = "root";
//    private static final String password = "root";
//
//    public static void main(String[] args) {
//        DataSourceManager dsm = DataSourceManager.getDsm();
//
//        DataSource dataSource = dsm.getDataSource(url, user, password);
//
//        DbExecutor executor = DbExecutorFactory.getExecutor(DbType.MYSQL);
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
//        int i = executor.batchInsert(dataSource, Student.class, list);
//
//        System.out.println(i);
//    }
//}
