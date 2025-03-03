package com.payment.simulator.common.sensors.service;


import com.payment.simulator.common.sensors.bo.SensorsBaseBO;

/**
 * 
 * @description
 * @date 2022/3/16
 */
public interface SensorsService {

    /**
     * 发送埋点数据
     *
     * @param sensorsBaseBO
     */
    void send(SensorsBaseBO sensorsBaseBO);
}
