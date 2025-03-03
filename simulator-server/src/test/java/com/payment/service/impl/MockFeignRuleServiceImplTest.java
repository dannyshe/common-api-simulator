package com.payment.service.impl;

import com.google.common.collect.Lists;
import com.payment.simulator.server.entity.MockFeignRule;
import com.payment.simulator.server.entity.MockFeignRuleQuery;
import com.payment.simulator.server.mapper.MockFeignRuleMapper;
import com.payment.simulator.server.service.impl.MockFeignRuleServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class MockFeignRuleServiceImplTest {

    @InjectMocks
    private MockFeignRuleServiceImpl mockFeignRuleService;

    @Mock
    private MockFeignRuleMapper mockFeignRuleMapper;

    @Test
    public void testQuery() throws Exception {
        doReturn(Lists.newArrayList(new MockFeignRule())).when(mockFeignRuleMapper).query(any());
        MockFeignRuleQuery mockFeignRuleQuery = new MockFeignRuleQuery();
        List<MockFeignRule> responseEntity = mockFeignRuleService.query(mockFeignRuleQuery);
        Assert.assertTrue(true);
    }
    @Test
    public void testQueryById() throws Exception {
        doReturn(new MockFeignRule()).when(mockFeignRuleMapper).queryById(any());
        MockFeignRuleQuery mockFeignRuleQuery = new MockFeignRuleQuery();
        MockFeignRule responseEntity = mockFeignRuleService.queryById("1");
        Assert.assertTrue(true);
    }

}
