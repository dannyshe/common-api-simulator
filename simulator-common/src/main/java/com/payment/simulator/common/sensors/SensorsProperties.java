package com.payment.simulator.common.sensors;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "payment.sensors")
public class SensorsProperties {

    /**
     * 神策埋点推送地址
     */
    private String serverUrl = "";
    /**
     * 神策埋点bulk大小，达到此值后触发推送
     */
    private Integer bulkSize = 50;
    /**
     * 内存中数据最大缓存条数，如此值大于0，代表缓存的数据会有条数限制，最小 3000 条，最大 6000 条。否则无条数限制。
     */
    private Integer maxCacheSize = 3000;
    /**
     * 数据同步失败是否抛出异常
     */
    private boolean throwException = Boolean.TRUE;

    /**
     * 是否调试
     */
    private boolean debug = Boolean.FALSE;

    /**
     * 神策出入金结果事件名称
     */
    private String withdrawDepositEventName = "withdraw_deposit_result";
}
