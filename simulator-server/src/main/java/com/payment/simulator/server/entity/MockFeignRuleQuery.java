package com.payment.simulator.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @version v1.0
 * @date 2022/4/7
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MockFeignRuleQuery {
    private String feignMock;
    private String fromSystem;
    private String toSystem;
    private String path;
    private Integer status;
}
