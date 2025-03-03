package com.payment.simulator.server.mapper;

import com.payment.simulator.server.entity.MockFeignRule;
import com.payment.simulator.server.entity.MockFeignRuleQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <pre>
 * Author Danny	2022年04月07日 created
 * </pre>
 */
@Repository
public interface MockFeignRuleMapper {

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