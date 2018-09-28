package com.renren.jinkong.kylin.dbtool.exception;

/**
 * 表格数据不匹配
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/26 16:53
 */
public class ExcelDataNoMatchException extends DbToolException {
    public ExcelDataNoMatchException() {
        super();
    }

    public ExcelDataNoMatchException(String message) {
        super(message);
    }

    public ExcelDataNoMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelDataNoMatchException(Throwable cause) {
        super(cause);
    }
}
