package com.payment.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.payment.bo.HttpServletRequestImpl;
import com.payment.simulator.server.bo.MockContext;
import com.payment.simulator.server.bo.request.MockRuleRequest;
import com.payment.simulator.server.bo.response.MockRuleResponse;
import com.payment.simulator.server.controller.MockRuleController;
import com.payment.simulator.server.entity.MockRule;
import com.payment.simulator.server.service.IMockRuleService;
import com.payment.simulator.common.dto.response.BasePaginationResponse;
import com.payment.simulator.common.dto.response.GenericResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class MockControllerTest {

    @InjectMocks
    private MockRuleController mockRuleController;


    @Mock
    private IMockRuleService mockRuleService;


    @Test
    public void mockData() throws Exception {
        doReturn(ResponseEntity.ok("data")).when(mockRuleService).mockData(any());
        Assert.assertEquals("data", mockRuleController.doMock(new HttpServletRequestImpl(), null).getBody());
    }

    @Test
    public void mockData2() throws Exception {
        MockContext mockContext = new MockContext();
        mockContext.setChannelId("");
        mockContext.setContentType("");
        mockContext.setRequestMethod("");
        mockContext.setRequestPath("");
        mockContext.setRequestHeader(new JSONObject());
        mockContext.setRequestParam(new JSONObject());
        mockContext.setRequestBody(new JSONObject());
        mockContext.setCacheBody(new JSONObject());
        mockContext.setPathValueMap(new JSONObject());
        mockContext.setExtraData(new JSONObject());

        doReturn(ResponseEntity.ok("data")).when(mockRuleService).mockData(any());
        Assert.assertEquals("data", mockRuleController.mock(mockContext).getBody());
    }

    @Test
    public void queryRule() {
        BasePaginationResponse<MockRuleResponse> paginationReponseBaseDto = new BasePaginationResponse<>(1, 20, 20, Lists.newArrayList(new MockRuleResponse()));
        doReturn(paginationReponseBaseDto).when(mockRuleService).queryMockRules(any());
        MockRuleRequest mockRuleRequest = new MockRuleRequest();
        mockRuleRequest.setId("");
        mockRuleRequest.setChannelId("");
        mockRuleRequest.setPath("");
        mockRuleRequest.setContentType("");
        mockRuleRequest.setTemplateCode("");
        mockRuleRequest.setReqRegRule("");
        mockRuleRequest.setReqJsonPath("");
        mockRuleRequest.setReqMatchRule("");
        mockRuleRequest.setStatusCode("");
        mockRuleRequest.setResponseTemplate("");
        mockRuleRequest.setPageNumber(0);
        mockRuleRequest.setPageSize(0);
        GenericResponse<BasePaginationResponse<MockRuleResponse>> paginationReponseBaseDtoGenericResult = mockRuleController.queryMockRule(mockRuleRequest);
        Assert.assertEquals(20, paginationReponseBaseDtoGenericResult.getData().getTotalNum());
    }


    @Test
    public void insertMockRule() {
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
        doReturn(mockRule).when(mockRuleService).insertMockRule(any());
        MockRuleRequest mockRuleRequest = new MockRuleRequest();
        mockRuleRequest.setId("");
        mockRuleRequest.setChannelId("");
        mockRuleRequest.setPath("");
        mockRuleRequest.setContentType("");
        mockRuleRequest.setTemplateCode("");
        mockRuleRequest.setReqRegRule("");
        mockRuleRequest.setReqJsonPath("");
        mockRuleRequest.setReqMatchRule("");
        mockRuleRequest.setStatusCode("");
        mockRuleRequest.setResponseTemplate("");
        mockRuleRequest.setPageNumber(0);
        GenericResponse<MockRule> result = mockRuleController.createMockRule(mockRuleRequest);
        Assert.assertEquals("111", result.getData().getId());
    }

    @Test
    public void updateMockRule() {

        doReturn(Boolean.TRUE).when(mockRuleService).updateMockRule(any());
        MockRuleRequest mockRule = new MockRuleRequest();
        mockRule.setId("1111");
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
        GenericResponse<Boolean> result = mockRuleController.updateMockRule(mockRule);
        Assert.assertEquals(Boolean.TRUE, result.getData());
    }

    @Test
    public void deleteMockRule() {
        doReturn(Boolean.TRUE).when(mockRuleService).delteMockRule(any());
        MockRuleRequest mockRule = new MockRuleRequest();
        mockRule.setId("1111");
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
        GenericResponse<Boolean> result = mockRuleController.deleteMockRule(mockRule);
        Assert.assertEquals(Boolean.TRUE, result.getData());
    }
}
