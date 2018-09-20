package com.renren.jinkong.kylin.dbtool;

import com.renren.jinkong.kylin.dbtool.anno.GeneratedValue;
import com.renren.jinkong.kylin.dbtool.anno.Id;
import com.renren.jinkong.kylin.dbtool.anno.Mapping;
import com.renren.jinkong.kylin.dbtool.anno.Table;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 14:19
 */
@Table(name = "tb_student")
public class Student {
    @Id
    @GeneratedValue
    private int id;
    @Mapping(cellName = "姓名")
    private String name;
    @Mapping(cellName = "地址")
    private String address;
    @Mapping(cellName = "学号")
    private String stuNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", stuNo='" + stuNo + '\'' +
                '}';
    }
}
