package com.payment.simulator.common.enums;

public enum FundDirection {
    PAYIN("1"),
    PAYOUT("2"),
    TRANSFER("3"),
    PCC_BUY("4"),
    PCC_SELL("5");

    private String directionId;
    private FundDirection(String directionId){
        this.directionId = directionId;
    }

    public String getDirectionId() {
        return directionId;
    }
}
