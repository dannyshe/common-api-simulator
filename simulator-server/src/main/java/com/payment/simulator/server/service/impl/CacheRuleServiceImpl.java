package com.payment.simulator.server.service.impl;

import com.payment.simulator.common.dto.response.BasePaginationResponse;
import com.payment.simulator.server.bo.CacheRuleBO;
import com.payment.simulator.server.bo.response.CacheRuleResponse;
import com.payment.simulator.server.entity.CacheRule;
import com.payment.simulator.server.entity.CacheRuleQuery;
import com.payment.simulator.server.mapper.CacheRuleMapper;
import com.payment.simulator.server.service.ICacheRuleService;
import com.payment.simulator.common.exception.PaymentException;
import com.payment.simulator.common.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.payment.simulator.common.exception.ErrorCode.PARAM_ERROR;
import static com.payment.simulator.common.exception.ErrorCode.VALIDATE_ERROR;

/**
 * @author Danny
 * @version 0.0.1
 * @date 2022/03/31
 */
@Slf4j
@Service
public class CacheRuleServiceImpl implements ICacheRuleService {

    @Autowired
    private CacheRuleMapper cacheRuleMapper;

    @Override
    public BasePaginationResponse<CacheRuleResponse> queryCacheRules(CacheRuleQuery cacheRuleQuery) {
        Integer count = cacheRuleMapper.queryCount(cacheRuleQuery);
        BasePaginationResponse<CacheRuleResponse> pagenationResponse = new BasePaginationResponse<>(cacheRuleQuery.getPageNumber(), cacheRuleQuery.getPageSize(), count, null);
        if (count > 0) {
            pagenationResponse.setItems(BeanUtils.copyListProperties(cacheRuleMapper.query(cacheRuleQuery), CacheRuleResponse.class));
        }
        return pagenationResponse;
    }

    @Override
    public CacheRule insertCacheRule(CacheRule cacheRule) {
        cacheRule.setId(UUID.randomUUID().toString());
        cacheRule.setCreated(new Date());
        cacheRuleMapper.insert(cacheRule);
        return cacheRuleMapper.queryById(cacheRule.getId());
    }

    @Override
    public Boolean updateCacheRule(CacheRule cacheRule) {
        if (StringUtils.isEmpty(cacheRule.getId())) {
            throw new PaymentException(PARAM_ERROR, "id是必填的");
        }
        if (cacheRuleMapper.queryCount(CacheRuleQuery.builder().id(cacheRule.getId()).build()) == 0) {
            throw new PaymentException(VALIDATE_ERROR, "无对应id的数据");
        }
        cacheRule.setUpdated(new Date());
        Integer update = cacheRuleMapper.update(cacheRule);
        return update > 0;
    }

    @Override
    public Boolean deleteCacheRule(String id) {
        Integer delete = cacheRuleMapper.delete(id);
        return delete > 0;
    }

    @Override
    public CacheRuleBO queryCacheRuleByMockId(String mockRuleId) {
        List<CacheRule> cacheRules = cacheRuleMapper.query(CacheRuleQuery.builder().mockRuleId(mockRuleId).build());
        if (CollectionUtils.isNotEmpty(cacheRules)) {
            return BeanUtils.copyProperties(cacheRules.get(0), CacheRuleBO.class);
        }
        return null;
    }
}
