package com.payment.simulator.common.enums;

public enum BankTransferUserKycStatus {
    VERIFYED("verifyed"),
    UNVERIFIED("unverified"),
    VERIFYING("verifying"),
    NOT_CERTIFIED("notCertified"),
    DENIED("denied");

    private String code;

    BankTransferUserKycStatus(String code) {
        this.code = code;
    }

    public static BankTransferUserKycStatus get(String code) {
        for (BankTransferUserKycStatus entry : BankTransferUserKycStatus.values()) {
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
}
