package com.payment.simulator.common.enums;

import java.util.stream.Stream;

/**
 * 描述信息
 *
 * 
 * @createTime 2021-11-01
 */
public enum PaymentSystem {
    PAYMENT_API("01", "PaymentAPI"),
    PAYMENT_CORE("02", "PaymentCore"),
    CHANNEL_CORE("03", "ChannelCore"),
    MERCHANT_ACCOUNT("04", "MerchantAccount"),
    CONFIG_CENTER("05", "Configcenter"),
    PAYMENT_NOTIFICATION("06", "PaymentNotification"),
    PAYMENT_WEBHOOK("07", "PaymentWebhook"),
    CLEARING_CENTER("08", "ClearingCenter"),
    FILE_GATEWAY("09", "FileGateway"),
    FILE_PROCESSOR("10", "FileProcessor"),
    DATA_SERVICE("13", "DataService"),
    REFUND_SERVICE("14", "RefundService"),
    PAYMENT_SERVICE("15", "PaymentService"),
    TOKEN_SERVICE("16", "TokenService"),

    PAYMENT_DISPUTE("17", "PaymentDispute"),
    SETTLEMENT_SERVICE("18", "SettlementService"),
    LEDGER_COLLECTOR_SERVICE("19", "LedgerCollectorService"),
    LEDGER_CONSUMER_SERVICE("20", "LedgerConsumerService"),
    FX_PAYOUT_SERVICE("21", "FxPayoutService"),
    LOGISTICS_SERVICE("22", "LogisticsService"),
    FX_PAYOUT_CHANNEL_SERVICE("23", "FxPayoutChannelService"),
    ERROR_CORRECTION_SERVICE("24", "ErrorCorrectionService"),
    FIN_EXCHANGE_SERVICE("25", "FinExchangeService"),

    USER_MANAGEMENT_SERVICE("26", "userManagementService"),
    CHANNEL_CALLBACK_SERVICE("27", "channelCallBackService"),
    CHANNEL_TRANSACTIVE_SERVICE("28", "channelTransactiveService"),
    CHANNEL_CAPITUAL_SERVICE("29", "channelCapitualService"),
    CHANNEL_CHECKOUT_SERVICE("30", "channelCheckoutService"),
    PAYMENT_RISK_ADAPTOR("31","paymentRiskAdaptor"),
    PAYMENT_CURRENCY_CONVERSION("32", "paymentCurrencyConversionService"),
    CHANNEL_PLAID_SERVICE("33", "channelPlaidService"),

    PAYMENT_UNKNOWN("-1", "Unknown");


    private String systemId;
    private String desc;

    PaymentSystem(String systemId, String desc) {
        this.systemId = systemId;
        this.desc = desc;
    }

    public String getSystemId() {
        return systemId;
    }


    public String getDesc() {
        return desc;
    }

    public static PaymentSystem getPaymentSystemById(String systemId){
        if(systemId != null){
            return Stream.of(PaymentSystem.values())
                    .filter(e->e.getSystemId().equalsIgnoreCase(systemId))
                    .findFirst()
                    .orElse(PAYMENT_UNKNOWN);
        }
        return PAYMENT_UNKNOWN;
    }

    public static PaymentSystem getByDesc(String systemDesc){
        if(systemDesc != null){
            return Stream.of(PaymentSystem.values())
                    .filter(e->e.getDesc().equalsIgnoreCase(systemDesc))
                    .findFirst()
                    .orElse(PAYMENT_UNKNOWN);
        }
        return PAYMENT_UNKNOWN;
    }
}
