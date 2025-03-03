package com.payment.simulator.common.sensors.dto;

import java.util.Map;

import lombok.Data;

/**
 * @author minn
 * @description
 * @date 2022/3/16
 */
@Data
public class SensorsRequestDto {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 属性
     */
    private Map<String,Object> properties;
}
