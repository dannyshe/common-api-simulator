package com.payment.simulator.server.service.impl;

import com.payment.simulator.server.bo.CacheRuleBO;
import com.payment.simulator.server.entity.CacheRule;
import com.payment.simulator.common.utils.BeanUtils;
import com.payment.simulator.server.repository.CacheRuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.payment.simulator.common.exception.ErrorCode.PARAM_ERROR;
import static com.payment.simulator.common.exception.ErrorCode.VALIDATE_ERROR;

/**
 * 
 * @version 0.0.1
 * @date 2022/03/31
 */
@Slf4j
@Service
public class CacheRuleService{

    @Autowired
    private CacheRuleRepository cacheRuleRepository;

//    @Override
//    public BasePaginationResponse<CacheRuleResponse> queryCacheRules(CacheRuleQuery cacheRuleQuery) {
//        Integer count = cacheRuleMapper.queryCount(cacheRuleQuery);
//        BasePaginationResponse<CacheRuleResponse> pagenationResponse = new BasePaginationResponse<>(cacheRuleQuery.getPageNumber(), cacheRuleQuery.getPageSize(), count, null);
//        if (count > 0) {
//            pagenationResponse.setItems(BeanUtils.copyListProperties(cacheRuleMapper.query(cacheRuleQuery), CacheRuleResponse.class));
//        }
//        return pagenationResponse;
//    }

    public CacheRule insertCacheRule(CacheRule cacheRule) {
        cacheRule.setId(UUID.randomUUID().toString());
        cacheRule.setCreated(new Date());
        cacheRuleRepository.save(cacheRule);
        return cacheRuleRepository.save(cacheRule);
    }

    public void updateCacheRule(CacheRule cacheRule) {
        cacheRule.setUpdated(new Date());
        cacheRuleRepository.save(cacheRule);
    }

    public void deleteCacheRule(String id) {
        cacheRuleRepository.deleteById(id);
    }

    public CacheRuleBO queryCacheRuleByMockId(String mockRuleId) {
        List<CacheRule> cacheRules =
                cacheRuleRepository.queryByMockRuleId(mockRuleId);
        if (CollectionUtils.isNotEmpty(cacheRules)) {
            return BeanUtils.copyProperties(cacheRules.get(0), CacheRuleBO.class);
        }
        return null;
    }
}
