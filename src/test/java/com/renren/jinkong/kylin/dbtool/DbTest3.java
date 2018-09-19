package com.renren.jinkong.kylin.dbtool;

import com.renren.jinkong.kylin.dbtool.core.DefaultDataSourceExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 14:18
 */
public class DbTest3 {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/springboot_demo?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
        String user = "root";
        String password = "root";

        DefaultDataSourceExecutor executor = new DefaultDataSourceExecutor(url, user, password);

        List<Student> list = new ArrayList<>();

        Student student = new Student();

        student.setName("tianlixin");
        student.setStuNo("asda");
        student.setAddress("asdasd");

        list.add(student);

        int i = executor.batchInsert(Student.class, list);

        System.out.println(i);
    }
}
