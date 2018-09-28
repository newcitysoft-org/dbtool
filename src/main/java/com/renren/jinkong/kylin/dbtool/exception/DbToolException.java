package com.renren.jinkong.kylin.dbtool.exception;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/28 14:41
 */
public class DbToolException extends RuntimeException {

    public DbToolException() {
        super();
    }

    public DbToolException(String message) {
        super(message);
    }

    public DbToolException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbToolException(Throwable cause) {
        super(cause);
    }
}

