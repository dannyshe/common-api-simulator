package com.payment.simulator.server.template;

import com.payment.simulator.server.bo.MockContext;
import org.springframework.http.ResponseEntity;

/**
 * @author Danny
 * @version 0.0.1
 * @date 2022/04/06
 */
public interface MockTemplateInterface {

    ResponseEntity mockData(MockContext mockContext);

}