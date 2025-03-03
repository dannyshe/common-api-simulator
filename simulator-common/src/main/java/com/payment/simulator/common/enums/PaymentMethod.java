package com.payment.simulator.common.enums;

public enum PaymentMethod {
    DEFAULT("00", "default payment method"),
    BANK_TRANSFER("01", "bank transfer"),
    WALLET("03", "e-Wallet"),
    BANK_CARD("04", "Bank Card"),
    BALANCE("05", "Balance"),

    UNKNOWN("99", "Unknown Transaction Type");

    private String paymentMethodId;
    private String desc;

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public String getDesc() {
        return desc;
    }

    private PaymentMethod(String paymentMethodId, String desc){
        this.paymentMethodId = paymentMethodId;
        this.desc = desc;
    }

    public static PaymentMethod get(String paymentMethodId){
        for (PaymentMethod entry : PaymentMethod.values()) {
            if(entry.paymentMethodId == paymentMethodId){
                return  entry;
            }
        }
        return UNKNOWN;
    }
}
