package com.payment.simulator.server.constant;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @description
 * @date 2021/12/28
 */
@Data
@Component
public class DynamicConstants {

    /**
     * 银行转账 发起提现接口开关
     */
    @Value("${bank.transfer.payout.create.enable:true}")
    public Boolean bankTransferPayoutCreateEnable;

    /**
     * 用户接口全局限流，秒
     */
    @Value("${access.limit.global.user.seconds:1}")
    private int accessLimitGlobalUserSeconds;

    /**
     * 用户接口全局限流，最大访问数
     */
    @Value("${access.limit.global.user.max-count:100}")
    private int accessLimitGlobalUserMaxCount;

    /**
     * 用户接口，单个用户限流，秒
     */
    @Value("${access.limit.user.seconds:1}")
    private int accessLimitUserSeconds;

    /**
     * 用户接口，单个用户限流，最大访问数
     */
    @Value("${access.limit.user.max-count:1}")
    private int accessLimitUserMaxCount;

    /**
     * Admin接口限流，秒
     */
    @Value("${access.limit.admin.seconds:5}")
    private int accessLimitAdminSeconds;

    /**
     * Admin接口限流，最大访问数
     */
    @Value("${access.limit.admin.max-count:1}")
    private int accessLimitAdminMaxCount;
    /**
     * 卡绑定/解绑 risk event_code
     */
    @Value("${risk.card.binding.topic:PAYMENT_BIND_EVENT}")
    private String riskBindEventTopic;
    /**
     * 卡绑定/解绑 risk event_code
     */
    @Value("${risk.card.binding.code:50014811}")
    private Long cardCenterBindingRiskCode;

    /**
     * 风控消息version
     */
    @Value("${risk.event.version:1.0}")
    private String riskEventVersion;

    @Value("${open.white.list:true}")
    public Boolean whitelistSwitchOn;


    /**
     * 开关
     */
    @Value("#{${payment.api.channel.switch:{'WALLET_PAY_IN_SWITCH' : true}}}")
    private Map<String, Boolean> channelSwitchMap;
}