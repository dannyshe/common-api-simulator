package com.payment.simulator.common.enums;

public enum ChannelCardOrderStatus {
    CREATED("created", "订单已创建", Boolean.FALSE),
    PENDING_AUTH("pending_auth", "订单预授权进行中", Boolean.FALSE),
    AUTHORIZED("authorized", "订单已预授权", Boolean.FALSE),
    CAPTURED("captured", "订单已成功", Boolean.TRUE),
    FAILED("failed", "订单已失败", Boolean.TRUE),
    EXPIRED("expired", "订单已过期", Boolean.TRUE),
    CARD_VERIFIED("card_verified", "卡已验证", Boolean.TRUE),
    ;

    private String code;
    private String desc;
    private boolean isFinalStatus;

    ChannelCardOrderStatus(String code, String desc, boolean isFinalStatus) {
        this.code = code;
        this.desc = desc;
        this.isFinalStatus = isFinalStatus;
    }

    public static ChannelCardOrderStatus get(String code) {
        for (ChannelCardOrderStatus entry : ChannelCardOrderStatus.values()) {
            if (entry.getCode().equals(code)) {
                return entry;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isFinalStatus() {
        return isFinalStatus;
    }

    public void setFinalStatus(boolean finalStatus) {
        isFinalStatus = finalStatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
