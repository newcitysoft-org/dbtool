package com.renren.jinkong.kylin.dbtool;

import com.renren.jinkong.kylin.dbtool.anno.GeneratedValue;
import com.renren.jinkong.kylin.dbtool.anno.Id;
import com.renren.jinkong.kylin.dbtool.anno.Mapping;
import com.renren.jinkong.kylin.dbtool.anno.Table;

import java.util.Date;

@Table(name = "tb_money_detail")
public class MoneyDetail {

    /**
     * 自增主键
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * JV公司全称
     */
    @Mapping(cellName = "JV公司全称")
    private String fullName;

    /**
     * 股东性质
     */
    @Mapping(cellName = "股东性质")
    private String nature;

    /**
     * 名称
     */
    @Mapping(cellName = "名称")
    private String name;

    /**
     * 认缴金额
     */
    @Mapping(cellName = "认缴金额")
    private Double money;

    /**
     * 认缴时间
     */
    @Mapping(cellName = "认缴时间")
    private Date date;

    /**
     * 实缴金额
     */
    @Mapping(cellName = "实缴金额")
    private Double money2;

    /**
     * 出资形式
     */
    @Mapping(cellName = "出资形式")
    private String form;

    /**
     * 出资账户名
     */
    @Mapping(cellName = "出资账户名")
    private String accountName;

    /**
     * 出资账号
     */
    @Mapping(cellName = "出资账号")
    private String account;

    /**
     * 出资开户行
     */
    @Mapping(cellName = "出资开户行")
    private String accountBank;

    /**
     * 收款账户名
     */
    @Mapping(cellName = "收款账户名")
    private String acceptAccountName;

    /**
     * 收款账号
     */
    @Mapping(cellName = "收款账号")
    private String acceptAccount;

    /**
     * 收款开户行
     */
    @Mapping(cellName = "收款开户行")
    private String acceptAccountBank;

    /**
     * 增资金额
     */
    @Mapping(cellName = "增资金额")
    private Double addMoney;

    /**
     * 撤资时间
     */
    @Mapping(cellName = "撤资时间")
    private Double releaseMoney;

    /**
     * 撤资金额
     */
    @Mapping(cellName = "撤资金额")
    private Date releaseMoneyDate;

    /**
     * 工商注册资本金
     */
    @Mapping(cellName = "工商注册资本金")
    private Double registerMoney;

}