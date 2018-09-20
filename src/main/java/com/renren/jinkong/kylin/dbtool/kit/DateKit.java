package com.renren.jinkong.kylin.dbtool.kit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/20 13:47
 */
public final class DateKit {

    private DateKit() {}

    public static SimpleDateFormat getDateFormat(String date) {
        SimpleDateFormat sdf = null;

        String datetime1 = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
        String datetime2 = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}";
        String date1 = "\\d{4}-\\d{2}-\\d{2}";
        String date2 = "\\d{4}/\\d{2}/\\d{2}";

        if(date.matches(date1)) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else if(date.matches(date2)) {
            sdf = new SimpleDateFormat("yyyy/MM/dd");
        } else if(date.matches(datetime1)) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if(date.matches(datetime2)) {
            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }

        return sdf;
    }

    public static Date getDate(String date) throws ParseException {
        System.out.println(date);
        if(date == null || "".equals(date)) {
            return null;
        }

        SimpleDateFormat sdf = getDateFormat(date);
        return sdf.parse(date);
    }

    public static Date getDateByStr(String date) {
        System.out.println(date);
        return new Date(date);
    }

    public static void main(String[] args) {
        System.out.println(getDateByStr("Sun Jun 28 00:00:00 CST 2037"));
    }
}
