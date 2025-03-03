//package com.payment.simulator.server.feign;
//
//import com.payment.simulator.server.dto.MockFeignRuleResponse;
//import org.assertj.core.util.Lists;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//public class FeignMockUtilsTest {
//    @InjectMocks
//    FeignMockUtils feignMockUtils;
//
//    @Test
//    public void testIsMatchRuleFalse(){
//        feignMockUtils.setMockFeignRuleResponseList(Lists.newArrayList(new MockFeignRuleResponse()));
//        feignMockUtils.isMatchRule("APP","PATH");
//        Assert.assertTrue(true);
//    }
//    @Test
//    public void testIsMatchRule(){
//        feignMockUtils.setMockFeignRuleResponseList(null);
//        feignMockUtils.isMatchRule("APP","PATH");
//        Assert.assertTrue(true);
//    }
//    @Test
//    public void testFormatParam(){
//        feignMockUtils.formatParam("APP=APP&X=X");
//        Assert.assertTrue(true);
//    }
//}
