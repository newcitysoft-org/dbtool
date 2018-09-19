package com.renren.jinkong.kylin.dbtool.model;

/**
 * 数据库信息
 * 现支持mysql
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 10:39
 */
public class DbInfo {
    /**
     *  数据连接信息
     */
    private String jdbcUrl;
    /**
     * 数据库用户名
     */
    private String user;
    /**
     * 数据库密码
     */
    private String password;

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DbInfo(String jdbcUrl, String user, String password) {
        this.jdbcUrl = jdbcUrl;this.user = user;this.password = password;
    }

    public DbInfo() {
    }

    public static DbInfoBuilder builder() {
        return new DbInfoBuilder();
    }


    public static class DbInfoBuilder {
        private String jdbcUrl;
        private String user;
        private String password;


        public DbInfo build() {
            return new DbInfo(this.jdbcUrl, this.user, this.password);
        }

        public DbInfoBuilder password(String password) {
            this.password = password;return this;
        }

        public DbInfoBuilder user(String user) {
            this.user = user;return this;
        }

        public DbInfoBuilder jdbcUrl(String jdbcUrl) {
            this.jdbcUrl = jdbcUrl;return this;
        }
    }

    @Override
    public String toString() {
        return "DbInfo{" +
                "jdbcUrl='" + jdbcUrl + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
