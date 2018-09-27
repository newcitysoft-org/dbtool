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
        if(date == null || "".equals(date)) {
            return null;
        }

        SimpleDateFormat sdf = getDateFormat(date);
        return sdf.parse(date);
    }

    public static String getTimestamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");

        return sdf.format(date);
    }

    public static Date getDateByStr(String date) {
        return new Date(date);
    }

    public static boolean validate(String date) {
        String expr = "^(((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))|"				+ "((\\d{2}(([02468][048])|([13579][26]))[\\/]((((0?[13578])|(1[02]))[\\/]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\/]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\/]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\/]((((0?[13578])|(1[02]))[\\/]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\/]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\/]((0?[1-9])|(1[0-9])|(2[0-8])))))))";

        return date.matches(expr);
    }

    public static void main(String[] args) {
        System.out.println(validate("2018/09/27"));
    }
}
