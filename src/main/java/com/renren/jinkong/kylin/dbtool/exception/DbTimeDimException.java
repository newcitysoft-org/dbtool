package com.renren.jinkong.kylin.dbtool.exception;

/**
 * 数据时间维度异常
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/26 16:53
 */
public class DbTimeDimException extends DbToolException {
    public DbTimeDimException() {
        super();
    }

    public DbTimeDimException(String message) {
        super(message);
    }

    public DbTimeDimException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbTimeDimException(Throwable cause) {
        super(cause);
    }
}
