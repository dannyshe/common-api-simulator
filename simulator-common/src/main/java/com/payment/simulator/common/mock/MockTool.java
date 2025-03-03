//package com.payment.simulator.common.mock;
//
//import java.net.URL;
//import java.util.Objects;
//
//import javax.servlet.http.HttpServletRequest;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
///**
// * 
// * @description
// * @date 2022/4/26 14:08
// */
//@Slf4j
//public class MockTool {
//
////    /**
////     * 是否切换mock服务
////     * @return boolean true:切换 false:不切换
////     */
////    public static boolean whetherToSwitchMock(){
////        try {
////            String testMode =getRequestHeaderValue(MockParam.HEADER_TEST_MODE);
////            log.info("MockTool testMode:{}",testMode);
////            //不为空且为true
////            if (StringUtils.isNotEmpty(testMode) && "true".equalsIgnoreCase(testMode)){
////                //Config config = ConfigService.getAppConfig();
////                //Boolean apolloTestMode = config.getBooleanProperty(MockParam.APOLLO_TEST_MODE, Boolean.FALSE);
////                log.info("MockTool apolloTestMode:{}",apolloTestMode);
////                String channelId = config.getProperty(MockParam.APOLLO_MOCK_CHANNEL_CHANNEL_ID, null);
////                log.info("MockTool channelId:{}",channelId);
////                String mockUrl=config.getProperty(MockParam.APOLLO_MOCK_CHANNEL_URL,null);
////                log.info("MockTool mockUrl:{}",mockUrl);
////                //apollo testMode为true，不配置则默认为false
////                return StringUtils.isNotEmpty(channelId) && StringUtils.isNotEmpty(mockUrl) && Boolean.TRUE.equals(apolloTestMode);
////            }
////        }catch (Exception e){
////            log.warn("MockTool whetherToSwitchMock error:{}",e.getMessage());
////        }
////        //有异常不做任何切换
////        return false;
////    }
//
//    /**
//     * 获取header头数据
//     * @param headerKey
//     * @return
//     */
//    public static String getRequestHeaderValue(String headerKey){
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (Objects.nonNull(requestAttributes)){
//            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//            return request.getHeader(headerKey);
//        }
//        return null;
//    }
//
//    /**
//     * 获取mockUrl及mockChannel
//     * @param url
//     * @return left:url right:channelId
//     */
//    public static MockParam doGetMockParam(String url){
//        try {
//            URL netUrl = new URL(url);
//            String authority = netUrl.getAuthority();
//            int index=url.indexOf(authority)+authority.length();
//            String basePath = url.substring(0,index);
//            String apiPath =  url.substring(index);
//            //覆盖baseUrl，若未配置，则为原先的baseUrl，即不做任何覆盖
//            Config config = ConfigService.getAppConfig();
//            basePath=config.getProperty(MockParam.APOLLO_MOCK_CHANNEL_URL, basePath);
//            log.info("MockTool basePath:{}",basePath);
//            String mockUrl=basePath+apiPath;
//            String channelId=config.getProperty(MockParam.APOLLO_MOCK_CHANNEL_CHANNEL_ID, null);
//            return MockParam.builder()
//                    .apolloMockUrl(mockUrl)
//                    .apolloChannelId(channelId)
//                    .apolloTestMode(getRequestHeaderValue(MockParam.HEADER_TEST_MODE))
//                    .build();
//        }catch (Exception e){
//            log.warn("MockTool whetherToSwitchMock error:{}",e.getMessage());
//        }
//        //有异常不做任何切换
//        return MockParam.builder().build();
//    }
//}
