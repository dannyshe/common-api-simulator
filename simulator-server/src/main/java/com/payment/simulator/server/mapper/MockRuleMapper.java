package com.payment.simulator.server.mapper;

import com.payment.simulator.server.entity.MockRule;
import com.payment.simulator.server.entity.MockRuleQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <pre>
 * Author Danny	2022年04月07日 created
 * </pre>
 */
@Repository
public interface MockRuleMapper {

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    MockRule queryById(@Param("id") String id);

    /**
     * 根据条件查询
     *
     * @param query
     * @return
     */
    List<MockRule> query(MockRuleQuery query);

    /**
     * 查询条数
     *
     * @param query
     * @return
     */
    Integer queryCount(MockRuleQuery query);

    /**
     * 添加一条数据
     *
     * @param dto
     * @return
     */
    Integer insert(MockRule dto);

    /**
     * 根据id更新
     *
     * @param dto
     * @return
     */
    Integer update(MockRule dto);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    Integer delete(@Param("id") String id);

    List<MockRule> queryMockRegPathRule(@Param("channelId") String channelId, @Param("requestMethod") String requestMethod, @Param("contentType") String contentType);
}