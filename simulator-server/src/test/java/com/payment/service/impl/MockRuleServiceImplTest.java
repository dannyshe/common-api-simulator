package com.payment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.payment.simulator.server.service.impl.MockRuleServiceImpl;
import com.payment.simulator.server.util.BodyReaderHttpServletRequestWrapper;
import com.payment.simulator.server.util.RequestParamsToMap;
import com.payment.simulator.server.util.VelocityService;
import com.payment.simulator.server.util.XmlTool;
import com.payment.util.CommonBeanUtil;
import com.google.common.collect.Lists;
import com.payment.bo.HttpServletRequestImpl;
import com.payment.simulator.server.bo.MockContext;
import com.payment.simulator.server.bo.response.MockRuleResponse;
import com.payment.simulator.server.entity.MockRule;
import com.payment.simulator.server.entity.MockRuleQuery;
import com.payment.simulator.server.mapper.MockRuleMapper;
import com.payment.simulator.server.service.ICacheRuleService;
import com.payment.simulator.common.dto.response.BasePaginationResponse;
import com.payment.simulator.common.exception.PaymentException;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.payment.simulator.common.exception.ErrorCode.VALIDATE_ERROR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MockRuleServiceImplTest.Config.class)
public class MockRuleServiceImplTest {

    @InjectMocks
    private MockRuleServiceImpl mockRuleService;

    @Mock
    private ICacheRuleService cacheRuleService;
    @Mock
    private MockRuleMapper mockRuleMapper;

    @Mock
    VelocityService velocityService;
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void mockRule_main() throws Exception {
        HttpServletRequest httpServletRequest = new HttpServletRequestImpl();
        MockContext mockContext = checkAndParseRequest(httpServletRequest);
        List<MockRule> mockRuleBOS = new ArrayList<>();
        MockRule mockRuleBO = new MockRule();
        mockRuleBO.setId("1");
        mockRuleBO.setChannelId("aaa");
        mockRuleBO.setRequestMethod("POST");
        mockRuleBO.setPath("/api/test/1234567");
        mockRuleBO.setContentType("application/json");
        mockRuleBO.setTemplateCode("");
        mockRuleBO.setReqRegRule("");
        mockRuleBO.setReqJsonPath("");
        mockRuleBO.setReqMatchRule("def rule_script(){ return true }");
        mockRuleBO.setStatusCode("200");
        mockRuleBO.setRemark("11");
        mockRuleBO.setResponseTemplate("{\"ok\"}");
        mockRuleBOS.add(mockRuleBO);
        doReturn(mockRuleBOS).when(mockRuleMapper).query(any());
        doReturn("{\"ok\"}").when(velocityService).assignValue((MockContext) any(), any());
        doReturn(null).when(cacheRuleService).queryCacheRuleByMockId(any());
        ResponseEntity responseEntity = mockRuleService.mockData(mockContext);
//        CacheRuleBO cacheRuleBO = new CacheRuleBO();
//        cacheRuleBO.setId("2");
//        cacheRuleBO.setMockRuleId("1");
//        cacheRuleBO.setCacheOption("get");
//        cacheRuleBO.setReqCacheRule("def rule_script(){ return true }");
//        cacheRuleBO.setCacheBody("");
//        cacheRuleBO.setCacheBodyMatchRule("");
//        cacheRuleBO.setCacheTime(100L);
//        cacheRuleBO.setNullResponseTemplate("{\"ok\"");
//        cacheRuleBO.setResponseTemplate("{\"ok\"");
//        cacheRuleBO.setMatchErrorResponseTemplate("{\"ok\"");
//        cacheRuleBO.setMatchStatusCode("200");
//        cacheRuleBO.setNullMatchStatusCode("404");
//        cacheRuleBO.setMatchErrorStatusCode("500");
//        doReturn(cacheRuleBO).when(cacheRuleService).queryCacheRuleByMockId(any());
//        ResponseEntity responseEntity1 = mockRuleService.mockData(mockContext);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
//        Assert.assertEquals(responseEntity1.getStatusCode(), HttpStatus.OK);
    }

    private MockContext checkAndParseRequest(HttpServletRequest request) throws IOException, DocumentException {

        String channelId = request.getHeader("channel_id");
        if (StringUtils.isEmpty(channelId)) {
            throw new PaymentException(VALIDATE_ERROR, "header中channel_id不能为空");
        }
        MockContext mockContext = new MockContext();
        mockContext.setChannelId(channelId);
        mockContext.setRequestMethod(request.getMethod());
        mockContext.setContentType(request.getHeader(HttpHeaders.CONTENT_TYPE));
        mockContext.setRequestPath(request.getRequestURI());
        mockContext.setRequestParam(RequestParamsToMap.getParameterMap(request));
        String bodyMessage = new BodyReaderHttpServletRequestWrapper(request).getBody();
        JSONObject requestBody = new JSONObject();
        if (StringUtils.contains(request.getHeader(HttpHeaders.CONTENT_TYPE), MediaType.APPLICATION_XML_VALUE)) {
            requestBody = XmlTool.documentToJSONObject(bodyMessage);
        } else if (StringUtils.contains(request.getHeader(HttpHeaders.CONTENT_TYPE), MediaType.APPLICATION_JSON_VALUE)) {
            requestBody = JSONObject.parseObject(bodyMessage);
        }
        mockContext.setRequestBody(requestBody);
        return mockContext;

    }

    //    @Test
//    public void mockRule_cache() throws Exception {
//        HttpServletRequest httpServletRequest = new HttpServletRequetImpl();
//        List<MockRule> mockRuleBOS = new ArrayList<>();
//        MockRule mockRuleBO = new MockRule();
//        mockRuleBO.setId("1");
//        mockRuleBO.setChannelId("aaa");
//        mockRuleBO.setRequestMethod("POST");
//        mockRuleBO.setPath("/api/test/1234567");
//        mockRuleBO.setContentType("application/json");
//        mockRuleBO.setTemplateCode("");
//        mockRuleBO.setReqRegRule("");
//        mockRuleBO.setReqJsonPath("");
//        mockRuleBO.setReqMatchRule("def rule_script(){ return true }");
//        mockRuleBO.setStatusCode("200");
//        mockRuleBO.setRemark("11");
//        mockRuleBO.setResponseTemplate("{\"ok\"}");
//        mockRuleBOS.add(mockRuleBO);
//        doReturn(mockRuleBOS).when(mockRuleMapper).query(any());
//        doReturn("{\"ok\"}").when(velocityService).assignValue((MockContext) any(), any());
//        doReturn("{\"aa\":\"aa\"}").when(redisTemplate.boundValueOps(any())).get();
//        doReturn("{\"aa\":\"aa\"}").when(redisTemplate.boundValueOps(any())).set(any(),any(),any());
//        CacheRuleBO cacheRuleBO=new CacheRuleBO();
//        cacheRuleBO.setId("2");
//        cacheRuleBO.setMockRuleId("1");
//        cacheRuleBO.setCacheOption("get");
//        cacheRuleBO.setReqCacheRule("def rule_script() { return true }");
//        cacheRuleBO.setCacheBody("{\n" +
//                "\n" +
//                "\"aa\":\"aa\""+
//                "}");
//        cacheRuleBO.setCacheBodyMatchRule("");
//        cacheRuleBO.setCacheTime(100L);
//        cacheRuleBO.setNullResponseTemplate("{\"ok\"");
//        cacheRuleBO.setResponseTemplate("{\"ok\"");
//        cacheRuleBO.setMatchErrorResponseTemplate("{\"ok\"");
//        cacheRuleBO.setMatchStatusCode("200");
//        cacheRuleBO.setNullMatchStatusCode("404");
//        cacheRuleBO.setMatchErrorStatusCode("500");
//        doReturn(cacheRuleBO).when(cacheRuleService).queryCacheRuleByMockId(any());
//        ResponseEntity responseEntity = mockRuleService.mockData(httpServletRequest);
//        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
//    }
    @Test
    public void queryRule() {
        List<MockRule> mockRules = Lists.newArrayList(new MockRule());
        doReturn(mockRules).when(mockRuleMapper).query(any());
        doReturn(20).when(mockRuleMapper).queryCount(any());
        MockRuleQuery mockRuleQuery = new MockRuleQuery();
        mockRuleQuery.setId("");
        mockRuleQuery.setChannelId("");
        mockRuleQuery.setPath("");
        mockRuleQuery.setRequestMethod("");
        mockRuleQuery.setContentType("");
        mockRuleQuery.setTemplateCode("");
        mockRuleQuery.setReqRegRule("");
        mockRuleQuery.setReqJsonPath("");
        mockRuleQuery.setReqMatchRule("");
        mockRuleQuery.setStatusCode("");
        mockRuleQuery.setResponseTemplate("");
        mockRuleQuery.setCreated(new Date());
        mockRuleQuery.setUpdated(new Date());
        mockRuleQuery.setRemark("");
        mockRuleQuery.setPageNumber(0);
        mockRuleQuery.setNumPerPage(0);
        mockRuleQuery.setPageSize(0);
        BasePaginationResponse<MockRuleResponse> paginationReponseBaseDto = mockRuleService.queryMockRules(mockRuleQuery);
        Assert.assertEquals(20, paginationReponseBaseDto.getTotalNum());
    }


    @Test
    public void insertMockRule() {
        doReturn(1).when(mockRuleMapper).insert(any());
        MockRule mockRule = new MockRule();
        mockRule.setChannelId("111");
        mockRule.setPath("");
        mockRule.setRequestMethod("");
        mockRule.setContentType("");
        mockRule.setTemplateCode("");
        mockRule.setReqRegRule("");
        mockRule.setReqJsonPath("");
        mockRule.setReqMatchRule("");
        mockRule.setStatusCode("");
        mockRule.setResponseTemplate("");
        mockRule.setCreated(new Date());
        mockRule.setUpdated(new Date());
        mockRule.setRemark("");
        doReturn(mockRule).when(mockRuleMapper).queryById(any());
        MockRule mockRule1 = mockRuleService.insertMockRule(mockRule);
        Assert.assertEquals("111", mockRule1.getChannelId());
    }

    @Test
    public void updateMockRule() {
        doReturn(1).when(mockRuleMapper).update(any());
        MockRule mockRule = new MockRule();
        mockRule.setId("111");
        mockRule.setChannelId("");
        mockRule.setPath("");
        mockRule.setRequestMethod("");
        mockRule.setContentType("");
        mockRule.setTemplateCode("");
        mockRule.setReqRegRule("");
        mockRule.setReqJsonPath("");
        mockRule.setReqMatchRule("");
        mockRule.setStatusCode("");
        mockRule.setResponseTemplate("");
        mockRule.setCreated(new Date());
        mockRule.setUpdated(new Date());
        mockRule.setRemark("");
        doReturn(1).when(mockRuleMapper).queryCount(any());
        Boolean result = mockRuleService.updateMockRule(mockRule);
        Assert.assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void deleteMockRule() {
        doReturn(1).when(mockRuleMapper).delete(any());
        Boolean result = mockRuleService.delteMockRule("11");
        Assert.assertEquals(Boolean.TRUE, result);
    }

    static class Config {

        @Bean
        RedisTemplate redisTemplate() {
            return CommonBeanUtil.getBasicRedisTemplate();
        }
    }
}
