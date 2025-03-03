package com.payment.simulator.server.request;

import lombok.Data;

@Data
public class MockFeignRuleResponse {
    private Long id;
    private String feignMock;
    private String fromSystem;
    private String toSystem;
    private String path;
    private Integer status;
    private Integer priority;
    private String title;
}
