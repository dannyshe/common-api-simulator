package com.common.api.simulator.server.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @version 0.0.1
 * @date 2022/04/06
 */
@Getter
@AllArgsConstructor
public enum DateFormatEnum {

    YYYY_MM_DD_T_HH_MM_SS("YYYY_MM_DD_T_HH_MM_SS", "yyyy-MM-dd'T'HH:mm:ss"),
    YYYY_MM_DD_HH_MM_SS("YYYY_MM_DD_HH_MM_SS", "yyyy-MM-dd HH:mm:ss"),
    YYYY_MM_DD("YYYY_MM_DD", "yyyy-MM-dd"),
    YYYYMMDD("YYYYMMDD", "yyyyMMdd"),
    YYYY_MMDD_HHMMSS("YYYY_MMDD_HHMMSS", "yyyyMMddHHmmss"),
    YYYY_MM("YYYY_MM", "yyyyMM"), //支付取消
    YYYY_MMDD_HHMMSS_8("YYYY_MMDD_HHMMSS_8", "yyyy-MM-dd'T'HH:mm:ss+08:00"),
    YYYY_MM_DD_DOT("YYYY_MM_DD_DOT", "yyyy.MM.dd"),

    ;

    private String code;
    private String desc;

    /**
     * 通过Code获取订单类型Desc
     **/
    public static String getDescByCode(String code) {
        DateFormatEnum[] enums = DateFormatEnum.values();
        for (DateFormatEnum e : enums) {
            if (e.code.equals(code)) {
                return e.desc;
            }
        }
        return code;
    }


}
