package com.payment.simulator.server.request;

import lombok.Data;

import java.util.Map;

@Data
public class MockFeignRequest {
    private String feignMock;
    private String fromSystem;
    private String toSystem;
    private String path;
    private Map<String,Object> params;
    private String body;
}
