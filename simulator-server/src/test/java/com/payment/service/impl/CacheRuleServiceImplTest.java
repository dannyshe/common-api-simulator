package com.payment.service.impl;

import com.google.common.collect.Lists;
import com.payment.simulator.server.bo.response.CacheRuleResponse;
import com.payment.simulator.server.entity.CacheRule;
import com.payment.simulator.server.entity.CacheRuleQuery;
import com.payment.simulator.server.mapper.CacheRuleMapper;
import com.payment.simulator.common.dto.response.BasePaginationResponse;
import com.payment.simulator.server.service.impl.CacheRuleServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class CacheRuleServiceImplTest {

    @InjectMocks
    private CacheRuleServiceImpl cacheRuleService;

    @Mock
    private CacheRuleMapper cacheRuleMapper;

    @Test
    public void queryRule() {
        List<CacheRule> cacheRules = Lists.newArrayList(new CacheRule());
        doReturn(cacheRules).when(cacheRuleMapper).query(any());
        doReturn(20).when(cacheRuleMapper).queryCount(any());
        CacheRuleQuery cacheRuleQuery = new CacheRuleQuery();
        cacheRuleQuery.setId("");
        cacheRuleQuery.setMockRuleId("");
        cacheRuleQuery.setCacheOption("");
        cacheRuleQuery.setReqCacheRule("");
        cacheRuleQuery.setCacheBody("");
        cacheRuleQuery.setCacheBodyMatchRule("");
        cacheRuleQuery.setCacheTime(0L);
        cacheRuleQuery.setMatchErrorResponseTemplate("");
        cacheRuleQuery.setNullResponseTemplate("");
        cacheRuleQuery.setResponseTemplate("");
        cacheRuleQuery.setMatchStatusCode("");
        cacheRuleQuery.setNullMatchStatusCode("");
        cacheRuleQuery.setMatchErrorStatusCode("");
        cacheRuleQuery.setCreated(new Date());
        cacheRuleQuery.setUpdated(new Date());
        cacheRuleQuery.setPageNumber(0);
        cacheRuleQuery.setNumPerPage(0);
        cacheRuleQuery.setPageSize(0);

        BasePaginationResponse<CacheRuleResponse> cacheRuleResponsePaginationReponseBaseDto = cacheRuleService.queryCacheRules(cacheRuleQuery);
        Assert.assertEquals(20, cacheRuleResponsePaginationReponseBaseDto.getTotalNum());
    }


    @Test
    public void insertCacheRule() {
        CacheRule cacheRule = new CacheRule();
        cacheRule.setId("");
        cacheRule.setMockRuleId("111");
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
        doReturn(1).when(cacheRuleMapper).insert(any());
        doReturn(cacheRule).when(cacheRuleMapper).queryById(any());
        CacheRule cacheRule1 = cacheRuleService.insertCacheRule(cacheRule);
        Assert.assertEquals("111", cacheRule1.getMockRuleId());
    }

    @Test
    public void updateCacheRule() {
        doReturn(1).when(cacheRuleMapper).update(any());
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
        doReturn(1).when(cacheRuleMapper).queryCount(any());
        Boolean result = cacheRuleService.updateCacheRule(cacheRule);
        Assert.assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void deleteMockRule() {
        doReturn(1).when(cacheRuleMapper).delete(any());
        Boolean result = cacheRuleService.deleteCacheRule("11");
        Assert.assertEquals(Boolean.TRUE, result);
    }
}
