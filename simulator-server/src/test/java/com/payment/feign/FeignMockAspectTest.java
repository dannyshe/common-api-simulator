//package com.payment.simulator.server.feign;
//
//import com.google.common.collect.Maps;
//import com.payment.simulator.server.api.MockFeignClientFacade;
//import com.payment.simulator.server.dto.MockFeignResponse;
//import com.payment.simulator.server.entity.MockFeignRule;
//import com.payment.simulator.server.entity.MockFeignRuleQuery;
//import com.artbite008.common.dto.response.GenericResponse;
//import feign.Request;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.awt.geom.GeneralPath;
//import java.nio.charset.Charset;
//
///**
// * @author Danny
// * @version 1.0
// * @description
// * @date 2022/04/11
// */
//@RunWith(SpringRunner.class)
//public class FeignMockAspectTest {
//
//    @InjectMocks
//    FeignMockAspect feignMockAspect;
//
//    @Mock
//    FeignMockUtils feignMockUtils;
//    @Mock
//    MockFeignClientFacade mockFeignClientFacade;
//
//    @Test
//    public void testGetResponse() {
//        try {
//            MockFeignRule mockFeignRule = new MockFeignRule();
//            mockFeignRule.setFeignMock("");
//            mockFeignRule.setReqMatchRule("");
//            mockFeignRule.setId(1L);
//            mockFeignRule.setMockResponse("");
//            mockFeignRule.setPath("");
//            mockFeignRule.setFromSystem("");
//            mockFeignRule.setMockHttpCode(200);
//            mockFeignRule.setPriority(2);
//            mockFeignRule.setStatus(0);
//            mockFeignRule.setTitle("");
//
//            mockFeignRule.getFeignMock();
//            mockFeignRule.getMockResponse();
//            mockFeignRule.getReqMatchRule();
//            mockFeignRule.getPath();
//            mockFeignRule.getId();
//            mockFeignRule.getFromSystem();
//            mockFeignRule.getMockHttpCode();
//            mockFeignRule.getPriority();
//            mockFeignRule.getStatus();
//            mockFeignRule.getTitle();
//            mockFeignRule.getToSystem();
//
//            MockFeignRuleQuery mockFeignRuleQuery = new MockFeignRuleQuery();
//            mockFeignRuleQuery.setFeignMock("");
//            mockFeignRuleQuery.setToSystem("");
//            mockFeignRuleQuery.setPath("");
//            mockFeignRuleQuery.setFromSystem("");
//            mockFeignRuleQuery.setStatus(0);
//
//            mockFeignRuleQuery.getFeignMock();
//            mockFeignRuleQuery.getPath();
//            mockFeignRuleQuery.getFromSystem();
//            mockFeignRuleQuery.getStatus();
//            mockFeignRuleQuery.getToSystem();
//            Request request = Request.create("GET", "http://payment-api/v1/redo",
//                    Maps.newConcurrentMap(), "{\"a\":\"a\"}".getBytes(), Charset.forName("UTF-8"));
//            Mockito.doReturn(true).when(feignMockUtils).isMatchRule(ArgumentMatchers.any(), ArgumentMatchers.any());
//            MockFeignResponse mockFeignResponse = new MockFeignResponse();
//            mockFeignResponse.setHttpCode(200);
//            mockFeignResponse.setResult("success");
//            Mockito.doReturn(GenericResponse.success(mockFeignResponse)).when(mockFeignClientFacade).runningMockFeign(ArgumentMatchers.any());
//            feignMockAspect.getResponse(request);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Assert.assertNotNull(feignMockAspect);
//    }
//}