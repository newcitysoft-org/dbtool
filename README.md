## 表格到数据库的导向工具

该插件目的：实现数据表格到关系型数据库的导向操作。

现支持Mysql数据库。

### 使用步骤

#### 1.导入pom依赖

    <!--  mysql -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.44</version>
        <scope>provided</scope>
    </dependency>
    
    <!--  druid数据库连接池 -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>1.0.29</version>
        <scope>provided</scope>
    </dependency>
    
    <!--  poi插件 -->
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>3.10.1</version>
        <scope>provided</scope>
    </dependency>

    <!--  本插件：数据库导向工具 -->
    <dependency>
        <groupId>com.renren</groupId>
        <artifactId>renren-jinkong-kylin-dbtool</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    
#### 2.使用案例
##### model映射

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
         * 出资账号**
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
    
    
##### 执行操作
    // 具体SQL脚本和表格找开发者获取
    File file = new File("D:\\data\\车商资本金明细.xls");
    ExcelDbPump pump = new ExcelDbPump(url, user, password);

    // 设置文件对象
    pump.setFile(file);
    // 设置映射类
    pump.setClazz(MoneyDetail.class);
    // 设置表头行号
    pump.setHeadRowNum(1);
    // 设置起始行号
    pump.setStartRowNum(5);
    // 设置结束行号
    pump.setEndRowNum(20);
    // 执行并获取所影响行数
    int i = pump.execute();
    
    
### 第二版本：直接表格强映射到数据库表

* 注意：目前的提前删除为假删除操作；执行操作需客户端提供批次号。
#### 使用案例

    package com.renren.jinkong.kylin.dbtool;
    
    import com.renren.jinkong.kylin.dbtool.core.op.DbInMode;
    import com.renren.jinkong.kylin.dbtool.core.op.DbTimeDimension;
    import com.renren.jinkong.kylin.dbtool.core.op.ExcelType;
    import com.renren.jinkong.kylin.dbtool.excel.DirectExcelDbPump;
    import com.renren.jinkong.kylin.dbtool.kit.DateKit;
    import com.renren.jinkong.kylin.dbtool.model.DbOpDefinition;
    import org.junit.Test;
    
    import java.io.File;
    import java.util.Date;
    
    /**
     * @author lixin.tian@renren-inc.com
     * @date 2018/9/20 13:24
     */
    public class DirectExcelDbPumpTest2 {
    
        private static final String url = "jdbc:mysql://localhost:3306/springboot_demo?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
        private static final String user = "root";
        private static final String password = "root";
    
        /**
         * 默认添加（直接增加）
         * 采用默认的数据定义
         * @throws Exception
         */
        @Test
        public void test0() throws Exception {
            File file = new File("D:\\data\\车商资本金明细2.xls");
    
            DirectExcelDbPump pump = new DirectExcelDbPump(url, user, password);
    
            pump.setFile(file);
            pump.setDbTableName("tb_money_detail");
    
            int i = pump.execute(DateKit.getTimestamp());
            System.out.println(i);
        }
    
        /**
         * 直接增加
         *
         * @throws Exception
         */
        @Test
        public void test1() throws Exception {
            File file = new File("D:\\data\\车商资本金明细.xls");
    
            DbOpDefinition definition = new DbOpDefinition();
    
            definition.setInMode(DbInMode.ADD);
            definition.setHeadRowNum(1);
            definition.setStartRowNum(2);
    
            DirectExcelDbPump pump = new DirectExcelDbPump(url, user, password);
    
            pump.setFile(file);
            pump.setDbTableName("tb_money_detail");
            pump.setDefinition(definition);
    
            int i = pump.execute(DateKit.getTimestamp());
            System.out.println(i);
        }
    
        /**
         * 全删全增
         *
         * @throws Exception
         */
        @Test
        public void test2() throws Exception {
            File file = new File("D:\\data\\车商资本金明细.xls");
            // 创建数据定义对象
            DbOpDefinition definition = new DbOpDefinition();
            // 设置数据插入模式（该模式可以不设置）
            definition.setInMode(DbInMode.DELETE_AND_ADD);
            definition.setHeadRowNum(1);
            definition.setStartRowNum(2);
    
            DirectExcelDbPump pump = new DirectExcelDbPump(url, user, password);
    
            pump.setFile(file);
            pump.setDbTableName("tb_money_detail");
            pump.setDefinition(definition);
    
            int i = pump.execute(DateKit.getTimestamp());
            System.out.println(i);
        }
    
        /**
         * 月维度替换添加
         *
         * @throws Exception
         */
        @Test
        public void test3() throws Exception {
            File file = new File("D:\\data\\车商资本金明细.xls");
    
            DbOpDefinition definition = new DbOpDefinition();
    
            definition.setHeadRowNum(1);
            definition.setStartRowNum(2);
            definition.setInMode(DbInMode.DELETE_DATE_AND_ADD);
            definition.setDtm(DbTimeDimension.MONTH);
            definition.setExcelType(ExcelType.MONTH);
            definition.setDayOrMonth("2018-09");
    
            DirectExcelDbPump pump = new DirectExcelDbPump(url, user, password);
    
            pump.setFile(file);
            pump.setDbTableName("tb_money_detail_his_month");
            pump.setDefinition(definition);
    
            int i = pump.execute(DateKit.getTimestamp());
            System.out.println(i);
        }
    
        /**
         * 日维度替换添加
         *
         * @throws Exception
         */
        @Test
        public void test4() throws Exception {
            File file = new File("D:\\data\\车商资本金明细.xls");
    
            DbOpDefinition definition = new DbOpDefinition();
    
            definition.setHeadRowNum(1);
            definition.setStartRowNum(2);
            definition.setInMode(DbInMode.DELETE_DATE_AND_ADD);
            definition.setDtm(DbTimeDimension.DAY);
            definition.setExcelType(ExcelType.DAY);
            definition.setDayOrMonth("2018-09-27");
    
            DirectExcelDbPump pump = new DirectExcelDbPump(url, user, password);
    
            pump.setFile(file);
            pump.setDbTableName("tb_money_detail_his_day");
            pump.setDefinition(definition);
    
            int i = pump.execute(DateKit.getTimestamp());
            System.out.println(i);
        }
    
        /**
         * 日维度替换添加（简化版本）
         *
         * @throws Exception
         */
        @Test
        public void test5() throws Exception {
            File file = new File("D:\\data\\车商资本金明细.xls");
    
            DbOpDefinition definition = new DbOpDefinition();
    
            definition.setHeadRowNum(1);
            definition.setStartRowNum(2);
            definition.setExcelType(ExcelType.DAY);
            definition.setDayOrMonth("2018-09-27");
    
            DirectExcelDbPump pump = new DirectExcelDbPump(url, user, password);
    
            pump.setFile(file);
            pump.setDbTableName("tb_money_detail_his_day");
            pump.setDefinition(definition);
    
            int i = pump.execute(DateKit.getTimestamp());
            System.out.println(i);
        }
