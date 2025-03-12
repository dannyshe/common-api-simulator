package com.payment.simulator.server.template;

import com.payment.simulator.server.dto.SimulateContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MockCodeExample implements MockTemplateInterface {

    @Override
    public ResponseEntity mockData(SimulateContext simulateContext) {

        return ResponseEntity.ok("data from Template template_test");
    }
}
