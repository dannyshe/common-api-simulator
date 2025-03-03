package com.payment.simulator.common.idgenerator;

public enum IDVersion {
    //transactionTime(yyyyMMddhhmmss 14) + systemId(2) + transactionTypeId(2) + cardTypeId(2) + merchantTypeId(1) + id version(1) + resvered digits(2) + sequence number(8)
    VERSION1("1"),
    //unused
    VERSION2("1");

    private String versionNumber;
    private IDVersion(String versionNumber){
        this.versionNumber = versionNumber;
    }

    public String getVersionNumber() {
        return versionNumber;
    }
}
