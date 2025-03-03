package com.payment.simulator.server.controller;

import com.payment.simulator.server.engine.MockFeignEngine;
import com.payment.simulator.server.entity.MockFeignRule;
import com.payment.simulator.server.entity.MockFeignRuleQuery;
import com.payment.simulator.server.request.MockFeignRequest;
import com.payment.simulator.server.request.MockFeignRuleRequest;
import com.payment.simulator.server.request.MockFeignRuleResponse;
import com.payment.simulator.server.service.IMockFeignRuleService;
import com.payment.simulator.common.dto.response.GenericResponse;
import com.payment.simulator.common.exception.ErrorCode;
import com.payment.simulator.common.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class MockFeignClientController{
    @Autowired
    private IMockFeignRuleService mockFeignRuleService;
    @Autowired
    private MockFeignEngine mockFeignEngine;

//    @LogAnnotation(name = "loadMockRule读取系统mock规则", detailLogType = LogNameConstants.FACADE_INFO)FACADE_INFO
    public GenericResponse<List<MockFeignRuleResponse>> loadMockRule(@RequestBody MockFeignRuleRequest request) {
        MockFeignRuleQuery query = new MockFeignRuleQuery();
        query.setFromSystem(request.getSystem());
        query.setStatus(0);
        List<MockFeignRule> resultList = mockFeignRuleService.query(query);
        return GenericResponse.success(BeanUtils.copyListProperties(resultList, MockFeignRuleResponse.class));
    }

    public GenericResponse<String> runningMockFeign(@RequestBody MockFeignRequest request) {
        MockFeignRuleQuery query = new MockFeignRuleQuery();
        query.setFromSystem(request.getFromSystem());
        query.setToSystem(request.getToSystem());
        query.setFeignMock(request.getFeignMock());
        query.setPath(request.getPath());
        query.setStatus(0);
        List<MockFeignRule> resultList = mockFeignRuleService.query(query);
        for (MockFeignRule rule : resultList) {
            if (mockFeignEngine.lunch(rule, request)) {
                return GenericResponse.success(rule.getMockResponse());//todo add mockHttpCode
            }
        }
        return GenericResponse.fail(ErrorCode.BUSINESS_ERROR.getCode(), "rule not match!");
    }

}
