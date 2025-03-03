package com.payment.simulator.server.controller;

import com.payment.simulator.server.bo.request.CacheRuleRequest;
import com.payment.simulator.server.bo.response.CacheRuleResponse;
import com.payment.simulator.server.entity.CacheRule;
import com.payment.simulator.server.entity.CacheRuleQuery;
import com.payment.simulator.server.service.ICacheRuleService;
import com.payment.simulator.common.dto.response.BasePaginationResponse;
import com.payment.simulator.common.dto.response.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.payment.simulator.common.utils.BeanUtils;

/**
 * @description:
 * 
 **/
@RestController
@Slf4j
public class CacheRuleController {
    @Autowired
    private ICacheRuleService cacheRuleService;


    @PostMapping("/v1/api/cache_rule/query")
    public GenericResponse<BasePaginationResponse<CacheRuleResponse>> queryCacheRule(
            @RequestBody CacheRuleRequest request) {
        CacheRuleQuery cacheRuleQuery = BeanUtils.copyProperties(request, CacheRuleQuery.class);
        return GenericResponse.success(cacheRuleService.queryCacheRules(cacheRuleQuery));
    }

    @PostMapping("/v1/api/cache_rule/create")
    public GenericResponse createCacheRule(
            @RequestBody CacheRuleRequest request) {
        CacheRule cacheRule = BeanUtils.copyProperties(request, CacheRule.class);
        return GenericResponse.success(cacheRuleService.insertCacheRule(cacheRule));
    }

    @PostMapping("/v1/api/cache_rule/update")
    public GenericResponse updateCacheRule(
            @RequestBody CacheRuleRequest request) {
        CacheRule cacheRule = BeanUtils.copyProperties(request, CacheRule.class);
        return GenericResponse.success(cacheRuleService.updateCacheRule(cacheRule));
    }

    @PostMapping("/v1/api/cache_rule/delete")
    public GenericResponse deleteCacheRule(
            @RequestBody CacheRuleRequest request) {
        CacheRule cacheRule = BeanUtils.copyProperties(request, CacheRule.class);
        return GenericResponse.success(cacheRuleService.deleteCacheRule(cacheRule.getId()));
    }
}
