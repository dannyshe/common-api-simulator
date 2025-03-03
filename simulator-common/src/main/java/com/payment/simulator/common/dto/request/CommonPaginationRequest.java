package com.payment.simulator.common.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CommonPaginationRequest extends BasePaginationRequest {
    private Map<String, Object> filter;
}
