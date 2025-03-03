package com.payment.simulator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交易类型枚举
 *
 * @date 2021年12月03日
 */
@AllArgsConstructor
@Getter
public enum TradeType {

    //老的充值类型-仅用于deposit微服务
    RECHARGE,

    //新的充值类型-用于新平台
    DEPOSIT,

    //提现
    WITHDRAW,

    //余额卖币
    SELL,

    //余额买币
    BUY,

    //快捷买币
    FAST_BUY,

    //快捷卖币
    FAST_SELL,

    //卡验证
    CARD_VALIDATE,

    //OTC卖币
    OTC_SELL,

    //OTC买币
    OTC_BUY,
}
