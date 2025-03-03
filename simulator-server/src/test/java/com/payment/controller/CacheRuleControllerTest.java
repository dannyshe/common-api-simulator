package com.payment.controller;

import com.google.common.collect.Lists;
import com.payment.simulator.server.bo.request.CacheRuleRequest;
import com.payment.simulator.server.bo.response.CacheRuleResponse;
import com.payment.simulator.server.controller.CacheRuleController;
import com.payment.simulator.server.entity.CacheRule;
import com.payment.simulator.server.service.ICacheRuleService;
import com.payment.simulator.common.dto.response.BasePaginationResponse;
import com.payment.simulator.common.dto.response.GenericResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class CacheRuleControllerTest {

    @InjectMocks
    private CacheRuleController cacheRuleController;


    @Mock
    private ICacheRuleService cacheRuleService;

    @Test
    public void queryRule() {
        BasePaginationResponse<CacheRuleResponse> paginationReponseBaseDto = new BasePaginationResponse<>(1, 20, 20, Lists.newArrayList(new CacheRuleResponse()));
        doReturn(paginationReponseBaseDto).when(cacheRuleService).queryCacheRules(any());
        CacheRuleRequest cacheRuleRequest = new CacheRuleRequest();
        cacheRuleRequest.setId("");
        cacheRuleRequest.setMockRuleId("");
        cacheRuleRequest.setCacheOption("");
        cacheRuleRequest.setReqCacheRule("");
        cacheRuleRequest.setCacheBody("");
        cacheRuleRequest.setCacheBodyMatchRule("");
        cacheRuleRequest.setCacheTime(0L);
        cacheRuleRequest.setResponseTemplate("");
        cacheRuleRequest.setNullReponseTemplate("");
        cacheRuleRequest.setMatchErrorResponseTemplate("");
        cacheRuleRequest.setMatchStatusCode("");
        cacheRuleRequest.setNullMatchStatusCode("");
        cacheRuleRequest.setMatchErrorStatusCode("");
        cacheRuleRequest.setPageNumber(0);
        cacheRuleRequest.setNumPerPage(0);
        cacheRuleRequest.setPageSize(0);
        GenericResponse<BasePaginationResponse<CacheRuleResponse>> paginationReponseBaseDtoGenericResult = cacheRuleController.queryCacheRule(cacheRuleRequest);
        Assert.assertEquals(20, paginationReponseBaseDtoGenericResult.getData().getTotalNum());
    }


    @Test
    public void insertCacheRule() {
        CacheRule cacheRule = new CacheRule();
        cacheRule.setId("111");
        cacheRule.setMockRuleId("");
        cacheRule.setCacheOption("");
        cacheRule.setReqCacheRule("");
        cacheRule.setCacheBody("");
        cacheRule.setCacheBodyMatchRule("");
        cacheRule.setCacheTime(0L);
        cacheRule.setMatchErrorResponseTemplate("");
        cacheRule.setNullResponseTemplate("");
        cacheRule.setResponseTemplate("");
        cacheRule.setMatchStatusCode("");
        cacheRule.setNullMatchStatusCode("");
        cacheRule.setMatchErrorStatusCode("");
        cacheRule.setCreated(new Date());
        cacheRule.setUpdated(new Date());
        doReturn(cacheRule).when(cacheRuleService).insertCacheRule(any());
        CacheRuleRequest cacheRuleRequest = new CacheRuleRequest();
        cacheRuleRequest.setId("111");
        cacheRuleRequest.setMockRuleId("");
        cacheRuleRequest.setCacheOption("");
        cacheRuleRequest.setReqCacheRule("");
        cacheRuleRequest.setCacheBody("");
        cacheRuleRequest.setCacheBodyMatchRule("");
        cacheRuleRequest.setCacheTime(0L);
        cacheRuleRequest.setResponseTemplate("");
        cacheRuleRequest.setNullReponseTemplate("");
        cacheRuleRequest.setMatchErrorResponseTemplate("");
        cacheRuleRequest.setMatchStatusCode("");
        cacheRuleRequest.setNullMatchStatusCode("");
        cacheRuleRequest.setMatchErrorStatusCode("");
        cacheRuleRequest.setPageNumber(0);
        cacheRuleRequest.setNumPerPage(0);
        cacheRuleRequest.setPageSize(0);
        GenericResponse<CacheRule> result = cacheRuleController.createCacheRule(cacheRuleRequest);
        Assert.assertEquals("111", result.getData().getId());
    }

    @Test
    public void updateCacheRule() {
        doReturn(Boolean.TRUE).when(cacheRuleService).updateCacheRule(any());
        CacheRuleRequest cacheRuleRequest = new CacheRuleRequest();
        cacheRuleRequest.setId("");
        cacheRuleRequest.setMockRuleId("");
        cacheRuleRequest.setCacheOption("");
        cacheRuleRequest.setReqCacheRule("");
        cacheRuleRequest.setCacheBody("");
        cacheRuleRequest.setCacheBodyMatchRule("");
        cacheRuleRequest.setCacheTime(0L);
        cacheRuleRequest.setResponseTemplate("");
        cacheRuleRequest.setNullReponseTemplate("");
        cacheRuleRequest.setMatchErrorResponseTemplate("");
        cacheRuleRequest.setMatchStatusCode("");
        cacheRuleRequest.setNullMatchStatusCode("");
        cacheRuleRequest.setMatchErrorStatusCode("");
        cacheRuleRequest.setPageNumber(0);
        cacheRuleRequest.setPageSize(0);
        GenericResponse<Boolean> result = cacheRuleController.updateCacheRule(cacheRuleRequest);
        Assert.assertEquals(Boolean.TRUE, result.getData());
    }

    @Test
    public void deleteCacheRule() {
        doReturn(Boolean.TRUE).when(cacheRuleService).deleteCacheRule(any());
        CacheRuleRequest cacheRuleRequest = new CacheRuleRequest();
        cacheRuleRequest.setId("");
        cacheRuleRequest.setMockRuleId("");
        cacheRuleRequest.setCacheOption("");
        cacheRuleRequest.setReqCacheRule("");
        cacheRuleRequest.setCacheBody("");
        cacheRuleRequest.setCacheBodyMatchRule("");
        cacheRuleRequest.setCacheTime(0L);
        cacheRuleRequest.setResponseTemplate("");
        cacheRuleRequest.setNullReponseTemplate("");
        cacheRuleRequest.setMatchErrorResponseTemplate("");
        cacheRuleRequest.setMatchStatusCode("");
        cacheRuleRequest.setNullMatchStatusCode("");
        cacheRuleRequest.setMatchErrorStatusCode("");
        cacheRuleRequest.setPageNumber(0);
        cacheRuleRequest.setPageSize(0);
        GenericResponse<Boolean> result = cacheRuleController.deleteCacheRule(cacheRuleRequest);
        Assert.assertEquals(Boolean.TRUE, result.getData());
    }
}
