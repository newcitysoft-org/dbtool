package com.renren.jinkong.kylin.dbtool;


import com.renren.jinkong.kylin.dbtool.anno.GeneratedValue;
import com.renren.jinkong.kylin.dbtool.anno.Id;
import com.renren.jinkong.kylin.dbtool.anno.Mapping;
import com.renren.jinkong.kylin.dbtool.anno.Table;

@Table(name = "tb_city_money_detail")
public class CityMoneyDetail {

    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * JV公司全称
     */
    @Mapping(cellName = "JV公司全称")
    private String name;

    /**
     * 利润分配去向
     */
    @Mapping(cellName = "利润分配去向")
    private String applyTo;

    /**
     * 支付类型
     */
    @Mapping(cellName = "支付类型")
    private String type;

    /**
     * 截至目前累计金额
     */
    @Mapping(cellName = "截至目前累计金额")
    private Double money;

}