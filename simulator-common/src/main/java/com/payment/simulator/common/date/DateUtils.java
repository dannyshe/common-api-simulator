package com.payment.simulator.common.date;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateUtils {

    /**
     * 获取默认时间格式: yyyy-MM-dd HH:mm:ss
     */
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateFormat.LONG_DATE_PATTERN_LINE.formatter;

    /**
     * String to LocalDateTime
     */
    public static LocalDateTime parse(String dateTime) {
        return LocalDateTime.parse(dateTime, DEFAULT_DATETIME_FORMATTER);
    }

    /**
     * 当前时间加days天
     * @param days 正数为加，负数为减
     * @return
     */
    public static LocalDateTime plusDays(long days){
        return LocalDateTime.now().plusDays(days);
    }

    /**
     * 目标时间加days天
     * @param source 目标时间
     * @param days 正数为加，负数为减
     * @return
     */
    public static LocalDateTime plusDays(LocalDateTime source,long days){
        return source.plusDays(days);
    }

    /**
     * String to LocalDateTime
     */
    public static LocalDateTime parse(String dateTime, DateFormat format) {
        return LocalDateTime.parse(dateTime, format.formatter);
    }

    /**
     * LocalDateTime to String
     */
    public static String to(LocalDateTime dateTime) {
        return DEFAULT_DATETIME_FORMATTER.format(dateTime);
    }

    /**
     * LocalDateTime to String
     */
    public static String to(LocalDateTime dateTime, DateFormat format) {
        return format.formatter.format(dateTime);
    }

    /**
     * 获取当前时间
     */
    public static String now() {
        return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间
     */
    public static String now(DateFormat format) {
        return format.formatter.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间距 1970-1-1 毫秒数
     */
    public static long currentMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取指定时间距 1970-1-1 毫秒数
     */
    public static long toMillis(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    /**
     * LocalDateTime转化成Date
     */
    public static Date local2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    /**
     * 时间格式
     */
    public enum DateFormat {

        /**
         * 短时间格式
         */
        SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
        SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
        SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
        SHORT_DATE_PATTERN_NONE("yyyyMMdd"),
        LONG_DATE_PATTERN_WITH_MILSEC_TIMEZONE_LINE_4("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"),

        /**
         * 长时间格式
         */
        LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),
        LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
        LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
        LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),
        LONG_DATE_PATTERN_COMPACT("yyyyMMddHHmmss"),
        LONG_DATE_PATTERN_COMPACT_NO_S("yyyyMMddHHmm"),


        /**
         * 长时间格式 带毫秒
         */
        LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_TIMEZONE_LINE("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
        LONG_DATE_PATTERN_WITH_MILSEC_TIMEZONE_LINE_2("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'"),
        LONG_DATE_PATTERN_WITH_MILSEC_TIMEZONE_LINE_3("yyyy-MM-dd'T'HH:mm:ss'Z'"),
        LONG_DATE_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMddHHmmssSSS");

        private transient DateTimeFormatter formatter;

        DateFormat(String pattern) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        }
    }
}