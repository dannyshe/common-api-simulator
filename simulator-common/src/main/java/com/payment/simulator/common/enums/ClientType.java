package com.payment.simulator.common.enums;

public enum ClientType {
    DEFAULT("0"),
    PERSONAL("1"),
    MERCHANT("2"),
    CORPORATE("3");

    private String clientTypeId;
    private ClientType(String clientTypeId){
        this.clientTypeId = clientTypeId;
    }
}
