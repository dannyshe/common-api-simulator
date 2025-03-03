package com.payment.simulator.server.entity;

import lombok.Data;

@Data
public class MockFeignRule {
    private Long id;
    private String feignMock;
    private String fromSystem;
    private String toSystem;
    private String path;
    private String reqMatchRule;
    private Integer mockHttpCode;
    private String mockResponse;
    private Integer status;
    private Integer priority;
    private String title;
}
