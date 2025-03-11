package com.payment.simulator.server.controller;

import com.payment.simulator.server.bo.request.CacheRuleRequest;
import com.payment.simulator.common.dto.response.GenericResponse;
import com.payment.simulator.server.service.CacheService;
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
    private CacheService cacheService;

}
