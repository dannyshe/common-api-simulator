package com.payment.simulator.common.sensors.service;

import com.payment.simulator.common.utils.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.payment.simulator.common.sensors.dto.SensorsRequestDto;
import com.sensorsdata.analytics.javasdk.ISensorsAnalytics;
import com.sensorsdata.analytics.javasdk.bean.EventRecord;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @description
 * @date 2022/3/16
 */
@Component
@Slf4j
public class SensorsClient {

    @Autowired
    private ISensorsAnalytics sensorsAnalytics;

    /**
     * 发送埋点
     * 
     * @param sensorsRequestDto
     */
    public void send(SensorsRequestDto sensorsRequestDto) {
        try {
            // 参数校验
            paramCheck(sensorsRequestDto);
            // 事件组装
            EventRecord eventRecord = EventRecord.builder()
                    .setDistinctId(sensorsRequestDto.getUserId())
                    .isLoginId(Boolean.TRUE)
                    .setEventName(sensorsRequestDto.getEventName())
                    .addProperties(sensorsRequestDto.getProperties())
                    .build();
            // 发送
            sensorsAnalytics.track(eventRecord);
        } catch (Exception e) {
            log.error(String.format("[神策埋点]-发送埋点异常，请求参数=%s", JSON.toJSONString(sensorsRequestDto)), e);
        }
    }

    private void paramCheck(SensorsRequestDto sensorsRequestDto) {
        AssertUtils.notNull(sensorsRequestDto.getProperties(), "事件属性为空");
    }
}
