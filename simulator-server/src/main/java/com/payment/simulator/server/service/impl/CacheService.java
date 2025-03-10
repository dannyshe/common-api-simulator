package com.payment.simulator.server.service.impl;

import com.payment.simulator.server.bo.CacheRuleBO;
import com.payment.simulator.server.entity.OldCacheRule;
import com.payment.simulator.common.utils.BeanUtils;
import com.payment.simulator.server.repository.OldCacheRuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 
 * @version 0.0.1
 * @date 2022/03/31
 */
@Slf4j
@Service
public class CacheService {

    @Autowired
    private OldCacheRuleRepository oldCacheRuleRepository;

//    @Override
//    public BasePaginationResponse<CacheRuleResponse> queryCacheRules(CacheRuleQuery cacheRuleQuery) {
//        Integer count = cacheRuleMapper.queryCount(cacheRuleQuery);
//        BasePaginationResponse<CacheRuleResponse> pagenationResponse = new BasePaginationResponse<>(cacheRuleQuery.getPageNumber(), cacheRuleQuery.getPageSize(), count, null);
//        if (count > 0) {
//            pagenationResponse.setItems(BeanUtils.copyListProperties(cacheRuleMapper.query(cacheRuleQuery), CacheRuleResponse.class));
//        }
//        return pagenationResponse;
//    }

    public OldCacheRule insertCacheRule(OldCacheRule oldCacheRule) {
        oldCacheRule.setId(UUID.randomUUID().toString());
        oldCacheRule.setCreated(new Date());
        oldCacheRuleRepository.save(oldCacheRule);
        return oldCacheRuleRepository.save(oldCacheRule);
    }

    public void updateCacheRule(OldCacheRule oldCacheRule) {
        oldCacheRule.setUpdated(new Date());
        oldCacheRuleRepository.save(oldCacheRule);
    }

    public void deleteCacheRule(String id) {
        oldCacheRuleRepository.deleteById(id);
    }

    public CacheRuleBO queryCacheRuleByMockId(String mockRuleId) {
        List<OldCacheRule> oldCacheRules =
                oldCacheRuleRepository.queryByMockRuleId(mockRuleId);
        if (CollectionUtils.isNotEmpty(oldCacheRules)) {
            return BeanUtils.copyProperties(oldCacheRules.get(0), CacheRuleBO.class);
        }
        return null;
    }
}
