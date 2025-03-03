package com.payment.simulator.server.enums;

import org.apache.commons.lang3.StringUtils;

public enum CacheOptionEnum {
    PUT("put"),
    GET("get");

    CacheOptionEnum(String name) {

    }

    public static CacheOptionEnum getOptionName(String optionName) {
        for (CacheOptionEnum contentTypeEnum : CacheOptionEnum.values()) {
            if (StringUtils.equalsIgnoreCase(optionName, contentTypeEnum.name())) {
                return contentTypeEnum;
            }
        }
        return null;
    }
}

