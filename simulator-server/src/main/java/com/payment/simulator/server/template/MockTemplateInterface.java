package com.payment.simulator.server.template;

import com.payment.simulator.server.dto.SimulateContext;
import org.springframework.http.ResponseEntity;

/**
 * 
 * @version 0.0.1
 * @date 2022/04/06
 */
public interface MockTemplateInterface {

    ResponseEntity mockData(SimulateContext simulateContext);

}