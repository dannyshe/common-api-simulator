package com.payment.simulator.server.controller;

import com.payment.simulator.server.bo.request.CacheRuleRequest;
import com.payment.simulator.server.entity.OldCacheRule;
import com.payment.simulator.common.dto.response.GenericResponse;
import com.payment.simulator.server.service.impl.CacheRuleService;
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
    private CacheRuleService cacheRuleService;


//    @PostMapping("/v1/api/cache_rule/query")
//    public GenericResponse<BasePaginationResponse<CacheRuleResponse>> queryCacheRule(
//            @RequestBody CacheRuleRequest request) {
//        CacheRuleQuery cacheRuleQuery = BeanUtils.copyProperties(request, CacheRuleQuery.class);
//        return GenericResponse.success(cacheRuleService.queryCacheRules(cacheRuleQuery));
//    }

    @PostMapping("/v1/api/cache_rule/create")
    public GenericResponse createCacheRule(
            @RequestBody CacheRuleRequest request) {
        OldCacheRule oldCacheRule = BeanUtils.copyProperties(request, OldCacheRule.class);
        return GenericResponse.success(cacheRuleService.insertCacheRule(oldCacheRule));
    }

    @PostMapping("/v1/api/cache_rule/update")
    public GenericResponse updateCacheRule(
            @RequestBody CacheRuleRequest request) {
        OldCacheRule oldCacheRule = BeanUtils.copyProperties(request, OldCacheRule.class);
        cacheRuleService.updateCacheRule(oldCacheRule);
        return GenericResponse.success();
    }

    @PostMapping("/v1/api/cache_rule/delete")
    public GenericResponse deleteCacheRule(
            @RequestBody CacheRuleRequest request) {
        OldCacheRule oldCacheRule = BeanUtils.copyProperties(request, OldCacheRule.class);
        cacheRuleService.deleteCacheRule(oldCacheRule.getId());
        return GenericResponse.success();
    }
}
