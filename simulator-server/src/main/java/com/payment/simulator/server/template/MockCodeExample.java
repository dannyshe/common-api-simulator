package com.payment.simulator.server.template;

import com.payment.simulator.server.annotation.Template;
import com.payment.simulator.server.bo.MockContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Template("template_test")
public class MockCodeExample implements MockTemplateInterface {

    @Override
    public ResponseEntity mockData(MockContext mockContext) {

        return ResponseEntity.ok("data from Template template_test");
    }
}
