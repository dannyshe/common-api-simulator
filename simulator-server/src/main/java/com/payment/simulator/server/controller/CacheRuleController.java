package com.payment.simulator.server.controller;

import com.payment.simulator.server.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

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
