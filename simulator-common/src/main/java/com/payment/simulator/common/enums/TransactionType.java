package com.payment.simulator.common.enums;

public enum TransactionType {
    DEFAULT("00", "Default transaction type"),
    CREATE_PAYMENT_ATTEMPT("01", "Attempt to confirm a intent"),
    CANCEL_PAYMENT_INTENT("03", "Cancel a payment intent"),

    AUTHORIZATION("10", "Default to be authorize 2D"),
    AUTHORIZATION_ONLY("11", "Only do authorization"),

    AUTHORIZATION3D("20", "3D Authorization"),
    ENROLLMENT("21", "1st step of 3D Authorization"),
    AUTHENTICATION("22", "2nd step of 3D Authorization"),
    CHECK_RESPONSE_3D("23", "3rd step of 3D Authorization"),
    CONFIRM_AUTHORIZATION_3D("24", "last step of 3D Authorization"),

    CAPTURE("30", "Capture"),
    CAPTURE_RETRY("33", "Capture Retry"),
    PURCHASE("31", "Authorize & Capture"),
    PURCHASE3D("32", "Authorize & Capture with 3DS"),

    REFUND("41", "Refund with Referral"),
    REFUND_NO_REFERRAL("42", "Refund without Referral"),
    REFUND_LOCK_BALANCE("43", "Refund lock balance from financial transaction"),
    VOIDCAPTURE("44", "Do refund by void capture"),

    VOID("50", "Cancel Succeeded Authorization"),
    VOID_SALES("51", "Cancel or Refund Sales"),
    CANCELREFUND("52", "Cancel Pending Refund"),

    CLEARING("60", "Clearing"),

    SETTLEMENT("70", "Settlement"),

    PAYMENT_CURRENCY_BUY("71", "payment currency buy"),
    PAYMENT_CURRENCY_SELL("72", "payment currency sell"),

    UNKNOWN("99", "Unknown Transaction Type");

    private String transactionTypeId;
    private String desc;

    public String getTransactionTypeId() {
        return transactionTypeId;
    }

    public String getDesc() {
        return desc;
    }

    private TransactionType(String transactionTypeId, String desc){
        this.transactionTypeId = transactionTypeId;
        this.desc = desc;
    }

    public static TransactionType get(String transactionTypeId){
        for (TransactionType entry : TransactionType.values()) {
            if(entry.transactionTypeId == transactionTypeId){
                return  entry;
            }
        }
        return UNKNOWN;
    }
}
