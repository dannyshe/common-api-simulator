package com.payment.simulator.server.engine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.payment.simulator.server.bo.MockContext;
import com.payment.simulator.server.entity.MockFeignRule;
import com.payment.simulator.server.request.MockFeignRequest;
import com.payment.simulator.server.util.VelocityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class MockFeignEngine {
    private static final String SCRIPT_NAME = "";
    @Autowired
    private VelocityService velocityService;

    public boolean lunch(MockFeignRule rule, MockFeignRequest request) {
        boolean isMatch = false;
        try {
            MockContext mockContext = new MockContext();
            mockContext.setRequestPath(request.getPath());
            if (Objects.nonNull(request.getParams())) {
                mockContext.setRequestParam(new JSONObject(request.getParams()));
            }
            mockContext.setRequestHeader(new JSONObject(ChainRequestContext.getCurrentContext()));
            if (StringUtils.isNotEmpty(request.getBody())) {
                try {
                    if(request.getBody().startsWith("[")){
                        JSONArray jsonArray = JSON.parseArray(request.getBody());
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("list",jsonArray);
                        mockContext.setRequestBody(jsonObject);
                    }else if(request.getBody().startsWith("{")){
                        mockContext.setRequestBody(JSON.parseObject(request.getBody()));
                    }else{
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("body",request.getBody());
                        mockContext.setRequestBody(jsonObject);
                    }
                }catch (Exception e){
                    log.error("mockContext.setRequestBody error",e.getMessage());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("body",request.getBody());
                    mockContext.setRequestBody(jsonObject);
                }
            }
            if (StringUtils.isNotEmpty(rule.getReqMatchRule())) {
                isMatch = GroovyScriptEngine.executeGroovyScript(mockContext, rule.getReqMatchRule());
            } else {
                isMatch = true;
            }
            if (isMatch) {
                rule.setMockResponse(velocityService.assignValue(mockContext, rule.getMockResponse()));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return isMatch;
    }
}
