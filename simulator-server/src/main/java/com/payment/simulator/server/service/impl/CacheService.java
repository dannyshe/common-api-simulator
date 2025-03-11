package com.payment.simulator.server.service.impl;

import com.payment.simulator.common.exception.SimulateException;
import com.payment.simulator.server.bo.CacheRuleBO;
import com.payment.simulator.server.bo.SimulateContext;
import com.payment.simulator.server.engine.GroovyScriptEngine;
import com.payment.simulator.server.entity.*;
import com.payment.simulator.common.utils.BeanUtils;
import com.payment.simulator.server.repository.DeleteActionRepository;
import com.payment.simulator.server.repository.OldCacheRuleRepository;
import com.payment.simulator.server.repository.QueryCacheActionRepository;
import com.payment.simulator.server.repository.SaveActionRepository;
import com.payment.simulator.server.util.VelocityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private DeleteActionRepository deleteActionRepository;

    @Autowired
    private SaveActionRepository saveActionRepository;

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
        List<OldCacheRule> oldCacheRules = oldCacheRuleRepository.queryByMockRuleId(mockRuleId);
        if (CollectionUtils.isNotEmpty(oldCacheRules)) {
            return BeanUtils.copyProperties(oldCacheRules.get(0), CacheRuleBO.class);
        }
        return null;
    }

    public String assembleAndSave(SimulateContext simulateContext, HitRule hitRule) {
        SaveAction saveAction = saveActionRepository.findById(hitRule.getActionId()).get();

        String objectId = GroovyScriptEngine.generateObjectId(simulateContext, saveAction.getGenerateKeyScript());
        String response = velocityService.assignValue(simulateContext, hitRule.getResponse(), objectId);
        if (StringUtils.isNotEmpty(saveAction.getGenerateKeyScript()) && saveAction.getCacheTtlHours() > 0) {
            if (!StringUtils.isEmpty(objectId)) {
                redisTemplate.boundValueOps(objectId).set(response, saveAction.getCacheTtlHours(), TimeUnit.HOURS);
            }
        }
        return response;
    }

    public String delete(SimulateContext simulateContext, HitRule hitRule) {
        DeleteAction deleteAction = deleteActionRepository.findById(hitRule.getActionId()).get();
        if (deleteAction == null) {
            throw new SimulateException(SERVER_ERROR, "DeleteAction not found with id: " +  hitRule.getActionId());
        }
        String objectId = GroovyScriptEngine.generateObjectId(simulateContext, deleteAction.getDeleteKeyScript());
        if(redisTemplate.delete(objectId)){
            return velocityService.assignValue(simulateContext, deleteAction.getResponse(), null);
        }else{
            return velocityService.assignValue(simulateContext, deleteAction.getNotfoundResponse(), null);
        }
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
