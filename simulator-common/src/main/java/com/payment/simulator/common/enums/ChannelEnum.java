package com.payment.simulator.common.enums;


/**
 * 描述信息
 *
 * 
 * @createTime 2021-11-01
 */
public enum ChannelEnum {
    UNKNOWN("unknown", "unknown", ChannelType.Unknown, "00", PaymentMethod.UNKNOWN),
    CHECKOUT("checkout", "Checkout", ChannelType.Card, "01", PaymentMethod.BANK_CARD),
    SEPA_TRANSACTIVE("sepa_transactive", "SEPA Transactive", ChannelType.BankTransfer, "02", PaymentMethod.BANK_TRANSFER, Boolean.TRUE),
    CAPITUAL("capitual", "Capitual", ChannelType.BankTransfer, "03", PaymentMethod.BANK_TRANSFER, Boolean.TRUE),
    ADVCASH("advcash", "advcash", ChannelType.Wallet, "04", PaymentMethod.WALLET, Boolean.TRUE),
    CHECKOUT_PCI("checkout_pci", "Checkout Pci", ChannelType.Card, "05", PaymentMethod.BANK_CARD, Boolean.TRUE),
    PLAID("plaid", "Plaid", ChannelType.Wallet, "06", PaymentMethod.WALLET, Boolean.TRUE),
    BALANCE("balance", "Balance", ChannelType.Others, "07", PaymentMethod.BALANCE, Boolean.FALSE),
    WALLESTER("wallester", "Wallester", ChannelType.Card, "08", PaymentMethod.BANK_CARD, Boolean.FALSE);

    private String desc;

    private String channelId;

    private ChannelType channelType;

    private String channelCode;

    private PaymentMethod paymentMethod;

    /**
     * 是否为法币支付方式
     */
    private boolean isFiat;

    public boolean isFiat() {
        return isFiat;
    }

    public void setFiat(boolean fiat) {
        isFiat = fiat;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    ChannelEnum(String channelId, String desc, ChannelType channelType, String channelCode, PaymentMethod paymentMethod) {
        this.channelId = channelId;
        this.desc = desc;
        this.channelType = channelType;
        this.channelCode = channelCode;
        this.paymentMethod = paymentMethod;
    }

    ChannelEnum(String channelId, String desc, ChannelType channelType, String channelCode, PaymentMethod paymentMethod, boolean isFiat) {
        this.channelId = channelId;
        this.desc = desc;
        this.channelType = channelType;
        this.channelCode = channelCode;
        this.paymentMethod = paymentMethod;
        this.isFiat = isFiat;
    }

    public static ChannelEnum get(String channelId) {
        for (ChannelEnum entry : ChannelEnum.values()) {
            if (entry.getChannelId().equals(channelId)) {
                return entry;
            }
        }
        return UNKNOWN;
    }

}
