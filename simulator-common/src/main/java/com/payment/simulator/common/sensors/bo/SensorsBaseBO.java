package com.payment.simulator.common.sensors.bo;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author minn
 * @description
 * @date 2022/3/16
 */
@Data
public class SensorsBaseBO {

    /**
     * 事件名称
     */
    @JSONField(serialize = false)
    private String eventName;

    /**
     * 用户id
     */
    @JSONField(serialize = false)
    private String userId;
}
