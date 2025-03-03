package com.payment.simulator.common.enums;

public enum SourceEnum {
    WEBHOOK("webhook"),
    WEB("web"),
    IOS("ios"),
    ANDROID("Android");

    private String type;

    private SourceEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
