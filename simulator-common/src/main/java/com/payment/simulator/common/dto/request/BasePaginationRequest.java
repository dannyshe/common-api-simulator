package com.payment.simulator.common.dto.request;

import com.payment.simulator.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePaginationRequest extends BaseDto {

    private static final long serialVersionUID = 8657620370406468880L;
    private Integer currentPage = 1;

    private Integer pageSize = 10;
}
