## 使用帮助

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
    
    
##### 执行操作

    File file = new File("D:\\data\\车商资本金明细.xls");
    ExcelDbPump pump = new ExcelDbPump(url, user, password);

    // 设置文件对象
    pump.setFile(file);
    // 设置映射类
    pump.setClazz(MoneyDetail.class);
    // 设置起始行号
    pump.setStartRowNum(1);
    // 设置结束行号
    pump.setEndRowNum(50);
        
    int i = pump.execute();