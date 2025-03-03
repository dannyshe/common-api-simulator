//package com.payment.simulator.server.engine;
//
//import com.google.common.collect.Maps;
//import com.payment.simulator.server.dto.MockFeignRequest;
//import com.payment.simulator.server.entity.MockFeignRule;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//public class MockFeignEngineTest {
//    @InjectMocks
//    private MockFeignEngine mockFeignEngine;
//
//    @Test
//    public void testLunch(){
//        MockFeignRule rule =new MockFeignRule();
//        rule.setReqMatchRule("xxxx");
//        MockFeignRequest request= new MockFeignRequest();
//        request.setPath("xxx");
//        request.setBody("{\"a\":\"a\"}");
//        request.setParams(Maps.newConcurrentMap());
//        mockFeignEngine.lunch(rule,request);
//        Assert.assertTrue(true);
//    }
//}
//
//
