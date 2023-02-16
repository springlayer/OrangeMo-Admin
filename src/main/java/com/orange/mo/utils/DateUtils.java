package com.orange.mo.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    /**
     * LocalDateTime 转字符串
     *
     * @param localDateTime
     * @return String
     */
    public static String localDateTimeToStr(LocalDateTime localDateTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(fmt);
    }

    /**
     * 字符串转LocalDateTime
     *
     * @param dateStr
     * @return LocalDateTime
     */
    public static LocalDateTime strToLocalDateTime(String dateStr) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateStr, fmt);
    }
}
