package com.bst.gitlab.common.utils;

import lombok.extern.log4j.Log4j2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author wanshuang
 * @Email wanshuang@scqcp.com
 * @Date 2021年01月13日 15:19
 * @Vsersion 1.0
 **/
@Log4j2
public class DateUtilEx  {

    /**
     * @Author wanshuang
     * @Description 返回yyyy-MM-dd的list
     * @Date 2021/1/13
     **/
    public static List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            // 日期加1(包含结束)
            tempEnd.add(Calendar.DATE, +1);
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (Exception e) {
            log.error("日期处理异常", e);
        }

        return days;
    }

    /**
     * 字符串类型时间(yyyy-MM-dd HH:mm:ss)转ISO8601时间格式
     * @param timestamp  字符串类型时间
     * @return
     */

    public static String getIso8601TimestampFromDateStr(String timestamp){
        java.time.format.DateTimeFormatter dtf1= java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        java.time.LocalDateTime ldt = java.time.LocalDateTime.parse(timestamp, dtf1);
        ZoneOffset offset = ZoneOffset.of("+08:00");
        OffsetDateTime date = OffsetDateTime.of(ldt, offset);
        java.time.format.DateTimeFormatter dtf2 = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        String res = date.format(dtf2);
        return res;
    }

    public static String yyyyMMddAddStartTime(String dateStr) {
        return dateStr+" 00:00:00";

    }

    public static String yyyyMMddAddEndTime(String dateStr) {
        return dateStr+" 23:59:59";

    }

    public static void main(String[] args){
          System.out.println(getIso8601TimestampFromDateStr("2022-04-26 00:00:00"));
          System.out.println(getIso8601TimestampFromDateStr("2022-04-27 00:00:00"));
    }

}
