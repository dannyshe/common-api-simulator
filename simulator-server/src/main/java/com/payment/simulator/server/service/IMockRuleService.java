package com.payment.simulator.server.service;

import com.payment.simulator.server.bo.MockContext;
import com.payment.simulator.server.bo.response.MockRuleResponse;
import com.payment.simulator.server.entity.MockRule;
import com.payment.simulator.server.entity.MockRuleQuery;
import com.payment.simulator.common.dto.response.BasePaginationResponse;
import org.springframework.http.ResponseEntity;

public interface IMockRuleService {

    ResponseEntity mockData(MockContext mockContext) throws Exception;

    BasePaginationResponse<MockRuleResponse> queryMockRules(MockRuleQuery bankTransferPayoutQueryRequestBo);

    MockRule insertMockRule(MockRule mockRule);

    Boolean updateMockRule(MockRule mockRule);

    Boolean delteMockRule(String id);

}
