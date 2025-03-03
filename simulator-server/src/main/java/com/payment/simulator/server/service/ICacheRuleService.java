package com.payment.simulator.server.service;

import com.payment.simulator.server.bo.CacheRuleBO;
import com.payment.simulator.server.bo.response.CacheRuleResponse;
import com.payment.simulator.server.entity.CacheRule;
import com.payment.simulator.server.entity.CacheRuleQuery;
import com.payment.simulator.common.dto.response.BasePaginationResponse;

public interface ICacheRuleService {

    BasePaginationResponse<CacheRuleResponse> queryCacheRules(CacheRuleQuery cacheRuleQuery);

    CacheRule insertCacheRule(CacheRule cacheRule);

    Boolean updateCacheRule(CacheRule cacheRule);

    Boolean deleteCacheRule(String id);

    CacheRuleBO queryCacheRuleByMockId(String mockRuleId);
}
