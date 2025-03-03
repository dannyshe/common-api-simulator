package com.payment.simulator.common.enums.account;

import lombok.Getter;

/**
 * 描述信息
 *
 * 
 * @createTime 2021-11-01
 */
@Getter
public enum DescriptionEnum {

    PAYMENT_SUCCESS("订单交易成功"),
    REFUND("退款"),
    REFUND_FAILED("退款失败"),
    CHARGEBACK_RAISED("被投诉问题交易"),
    CHARGEBACK_WON("申诉成功"),
    CHARGEBACK_RESOLVED("问题交易已解決"),
    CHARGEBACK_CANCELED("问题交易已取消"),
    WITHDRAW("提款"),
    REMITTANCE_FAILED("提款失败"),
    MARGIN_RESERVED("冻结保证金"),
    MARGIN_RELEASE("保证金释放"),
    SETTLEMENT("结算"),
    EXCHANGE("换汇"),
    EXCHANGE_FAILED("换汇失败"),
    LOGISTICS_UPLOAD("物流上传"),
    RECONCILIATION("调帐"),
    ARREARS("欠费");

    private String desc;

    DescriptionEnum(String desc) {
        this.desc = desc;
    }
}
