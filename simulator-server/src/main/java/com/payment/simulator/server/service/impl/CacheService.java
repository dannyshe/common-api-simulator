package com.payment.simulator.server.service.impl;

import com.payment.simulator.common.exception.SimulateException;
import com.payment.simulator.server.bo.CacheRuleBO;
import com.payment.simulator.server.bo.SimulateContext;
import com.payment.simulator.server.engine.GroovyScriptEngine;
import com.payment.simulator.server.entity.HitRule;
import com.payment.simulator.server.entity.OldCacheRule;
import com.payment.simulator.common.utils.BeanUtils;
import com.payment.simulator.server.entity.QueryCacheAction;
import com.payment.simulator.server.repository.OldCacheRuleRepository;
import com.payment.simulator.server.repository.QueryCacheActionRepository;
import com.payment.simulator.server.util.VelocityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.payment.simulator.common.exception.ErrorCode.SERVER_ERROR;

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

    @Autowired
    private VelocityService velocityService;

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    @Autowired
    private QueryCacheActionRepository queryCacheActionRepository;

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

    public void save(String objectId, String response, Integer cacheTTLHours) {
        redisTemplate.boundValueOps(objectId).set(response, cacheTTLHours, TimeUnit.HOURS);
    }

    public Object query(SimulateContext simulateContext, HitRule hitRule) {
        QueryCacheAction queryCacheAction = queryCacheActionRepository.findById(hitRule.getActionId()).get();
        if (queryCacheAction == null) {
            throw new SimulateException(SERVER_ERROR, "QueryCacheAction not found with id: " +  hitRule.getActionId());
        }
        String objectId = GroovyScriptEngine.generateObjectId(simulateContext, queryCacheAction.getQueryKeyScript());
        String response = redisTemplate.boundValueOps(objectId).get();
        if(StringUtils.isEmpty(response)){
            return velocityService.assignValue(simulateContext, queryCacheAction.getNotfoundResponse(), null);
        }

        return response;
    }


}
