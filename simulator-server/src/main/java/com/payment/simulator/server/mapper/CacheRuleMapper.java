package com.payment.simulator.server.mapper;

import com.payment.simulator.server.entity.CacheRule;
import com.payment.simulator.server.entity.CacheRuleQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <pre>
 * Author Danny	2022年04月07日 created
 * </pre>
 */
@Repository
public interface CacheRuleMapper {

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    CacheRule queryById(@Param("id") String id);

    /**
     * 根据条件查询
     *
     * @param query
     * @return
     */
    List<CacheRule> query(CacheRuleQuery query);

    /**
     * 查询条数
     *
     * @param query
     * @return
     */
    Integer queryCount(CacheRuleQuery query);

    /**
     * 添加一条数据
     *
     * @param dto
     * @return
     */
    Integer insert(CacheRule dto);

    /**
     * 根据id更新
     *
     * @param dto
     * @return
     */
    Integer update(CacheRule dto);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    Integer delete(@Param("id") String id);

}