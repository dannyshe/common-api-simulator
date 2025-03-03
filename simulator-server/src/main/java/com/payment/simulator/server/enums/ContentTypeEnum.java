package com.payment.simulator.server.enums;

import org.apache.commons.lang3.StringUtils;

public enum ContentTypeEnum {
    XML("xml"),
    JSON("json");

    ContentTypeEnum(String name) {

    }

    public static ContentTypeEnum getContentTypeByName(String contentTypeName) {
        for (ContentTypeEnum contentTypeEnum : ContentTypeEnum.values()) {
            if (StringUtils.equalsIgnoreCase(contentTypeName, contentTypeEnum.name())) {
                return contentTypeEnum;
            }
        }
        return null;
    }
}

