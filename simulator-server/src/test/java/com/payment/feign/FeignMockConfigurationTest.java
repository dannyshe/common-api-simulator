//package com.payment.simulator.server.feign;
//
//import com.google.common.collect.Lists;
//import com.payment.simulator.server.api.MockFeignClientFacade;
//import com.payment.simulator.server.dto.MockFeignRuleResponse;
//import com.artbite008.common.dto.response.GenericResponse;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.BDDMockito;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.internal.util.reflection.FieldSetter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.lang.reflect.Field;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = FeignMockConfigurationTest.Config.class)
//public class FeignMockConfigurationTest {
//    @InjectMocks
//    FeignMockConfiguration feignMockConfiguration;
//    @Mock
//    MockFeignClientFacade mockFeignClientFacade;
//
//    @Test
//    public void testInit() {
//        GenericResponse<List<MockFeignRuleResponse>> result = GenericResponse.success(Lists.newArrayList());
//        BDDMockito.given(mockFeignClientFacade.loadMockRule(ArgumentMatchers.any())).willReturn(result);
//        Field mockMode = null;
//        try {
//            mockMode = FeignMockConfiguration.class.getDeclaredField("mockMode");
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        }
//        FieldSetter.setField(feignMockConfiguration, mockMode, true);
//        feignMockConfiguration.init();
//        Assert.assertTrue(true);
//    }
//
//    static class Config {
//        @Bean
//        FeignMockUtils feignMockUtils() {
//            return new FeignMockUtils();
//        }
//
//    }
//}
