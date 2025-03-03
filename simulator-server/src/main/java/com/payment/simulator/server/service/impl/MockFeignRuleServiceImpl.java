package com.payment.simulator.server.service.impl;

import com.payment.simulator.server.entity.MockFeignRule;
import com.payment.simulator.server.entity.MockFeignRuleQuery;
import com.payment.simulator.server.mapper.MockFeignRuleMapper;
import com.payment.simulator.server.service.IMockFeignRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @version 0.0.1
 * @date 2022/03/31
 */
@Slf4j
@Service
public class MockFeignRuleServiceImpl implements IMockFeignRuleService {

    @Autowired
    private MockFeignRuleMapper mockFeignRuleMapper;

    @Override
    public MockFeignRule queryById(String id) {
        return mockFeignRuleMapper.queryById(id);
    }

    @Override
    public List<MockFeignRule> query(MockFeignRuleQuery query) {
        return mockFeignRuleMapper.query(query);
    }
}
