package com.renren.jinkong.kylin.dbtool.exception;

/**
 * 表格数据不匹配
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/26 16:53
 */
public class ExcelVersionException extends DbToolException {
    public ExcelVersionException() {
        super();
    }

    public ExcelVersionException(String message) {
        super(message);
    }

    public ExcelVersionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelVersionException(Throwable cause) {
        super(cause);
    }
}
