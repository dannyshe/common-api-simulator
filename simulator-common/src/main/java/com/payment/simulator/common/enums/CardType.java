package com.payment.simulator.common.enums;

public enum CardType {

    /**
     * 旧数据出现拼写错误UNKNOW,防止枚举转化异常,增加UNKNOW
     */
    DEFERRED, CREDIT, DEBIT, PREPAID, CHARGE, UNKNOWN, UNKNOW;

    public static CardType get(String name) {
        for (CardType entry : CardType.values()) {
            if (entry.name().equalsIgnoreCase(name)) {
                return entry;
            }
        }
        return UNKNOWN;
    }
}
