package com.payment.simulator.common.sensors.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.payment.simulator.common.sensors.bo.SensorsBaseBO;
import com.payment.simulator.common.sensors.dto.SensorsRequestDto;
import com.payment.simulator.common.sensors.service.SensorsClient;
import com.payment.simulator.common.sensors.service.SensorsService;
import com.payment.simulator.common.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SensorsServiceImpl implements SensorsService {

    @Autowired
    private SensorsClient sensorsClient;

    @Override
    public void send(SensorsBaseBO sensorsBo) {
        try {
            // 参数校验
            paramCheck(sensorsBo);
            // dto参数组装
            SensorsRequestDto dto = buildRequest(sensorsBo);
            log.info("[神策埋点]-开始发送神策埋点数据，事件类型={},请求参数={}", sensorsBo.getEventName(), JSON.toJSONString(dto));
            // 发送埋点事件
            sensorsClient.send(dto);
        } catch (Exception e) {
            log.error("[神策埋点]-发送埋点数据失败,请求参数={}", JSON.toJSONString(sensorsBo), e);
        }
    }

    private void paramCheck(SensorsBaseBO sensorsBo) {
        AssertUtils.notNull(sensorsBo.getEventName(), "事件名称为空");
        AssertUtils.notNull(sensorsBo.getUserId(), "用户id为空");
    }

    private SensorsRequestDto buildRequest(SensorsBaseBO sensorsBo) {
        SensorsRequestDto dto = new SensorsRequestDto();
        dto.setEventName(sensorsBo.getEventName());
        dto.setUserId(getUid(sensorsBo.getUserId()).toString());
        // 事件属性组装
        JSONObject properties = JSON.parseObject(JSON.toJSONString(sensorsBo));
        AssertUtils.notNull(properties, "事件参数为空");
        dto.setProperties(properties);
        return dto;
    }

    /**
     * 根据需求，需要将用户id改为传uid
     *
     * @param userId
     * @return
     */
    private Long getUid(String userId) {
        return null;
    }
}
