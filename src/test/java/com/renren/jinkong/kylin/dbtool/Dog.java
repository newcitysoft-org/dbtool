package com.renren.jinkong.kylin.dbtool;

import com.renren.jinkong.kylin.dbtool.anno.GeneratedValue;
import com.renren.jinkong.kylin.dbtool.anno.Id;
import com.renren.jinkong.kylin.dbtool.anno.Mapping;
import com.renren.jinkong.kylin.dbtool.anno.Table;

import java.util.Date;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 15:09
 */
@Table(name = "tb_dog")
public class Dog {
    @Id
    @GeneratedValue
    private int id;
    @Mapping(cellName = "狗名")
    private String name;
    @Mapping(cellName = "重量")
    private double weight;
    @Mapping(cellName = "年龄")
    private int age;
    @Mapping(cellName = "出生时间")
    private Date birthday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}
