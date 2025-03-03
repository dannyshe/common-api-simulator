package com.payment.simulator.server.service;

import com.payment.simulator.server.entity.MockFeignRule;
import com.payment.simulator.server.entity.MockFeignRuleQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMockFeignRuleService {
    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    MockFeignRule queryById(@Param("id") String id);

    /**
     * 根据条件查询
     *
     * @param query
     * @return
     */
    List<MockFeignRule> query(MockFeignRuleQuery query);
}
