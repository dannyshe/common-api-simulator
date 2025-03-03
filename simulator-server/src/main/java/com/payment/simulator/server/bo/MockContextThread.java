package com.payment.simulator.server.bo;


import com.payment.simulator.server.util.ThreadLocalUtil;

/**
 * 
 * @version 0.0.1
 * @date 2022/04/06
 */
public class MockContextThread {

//    public void set(String key, Object object) {
//        ThreadLocalUtil.set(key, object);
//    }
//
//    public void setChannelId(String channelId) {
//        Map<String, Object> requestHeader = ThreadLocalUtil.get(Constant.REQUEST_HEADER);
//        if (requestHeader == null) {
//            requestHeader = new HashMap<>();
//        }
//        requestHeader.put(Constant.CHANNEL_ID, channelId);
//        ThreadLocalUtil.set(Constant.REQUEST_HEADER, requestHeader);
//    }
//
//    public void setContentType(String contentType) {
//        Map<String, Object> requestHeader = ThreadLocalUtil.get(Constant.REQUEST_HEADER);
//        if (requestHeader == null) {
//            requestHeader = new HashMap<>();
//        }
//        requestHeader.put(Constant.CONTENT_TYPE, contentType);
//        ThreadLocalUtil.set(Constant.REQUEST_HEADER, requestHeader);
//    }
//
//    public void setRequestParam(Map<String, Object> requestParam) {
//        ThreadLocalUtil.set(Constant.REQUEST_PARAM, requestParam);
//    }
//
//    public void setRequestBody(Map<String, Object> requestBody) {
//        ThreadLocalUtil.set(Constant.REQUEST_BODY, requestBody);
//    }
//
//    public void setPath(String path) {
//        ThreadLocalUtil.set(Constant.REQUEST_PATH, path);
//    }
//
//    public void setCacheBody(Map<String, Object> cacheBody) {
//        ThreadLocalUtil.set(Constant.CACHE_BODY, cacheBody);
//    }
//
//    public void setMockRule(MockRuleBO mockRuleBO) {
//        ThreadLocalUtil.set(Constant.MOCK_RULE, mockRuleBO);
//    }
//
//    public String getChannelId() {
//        return MapUtils.getString(ThreadLocalUtil.get(Constant.REQUEST_HEADER), Constant.CHANNEL_ID);
//    }
//
//    public String getContentType() {
//        return MapUtils.getString(ThreadLocalUtil.get(Constant.REQUEST_HEADER), Constant.CONTENT_TYPE);
//    }
//
//    public Map<String, Object> getRequestHeader() {
//        return ThreadLocalUtil.get(Constant.REQUEST_HEADER);
//    }
//
//    public JSONObject getRequestBody() {
//        return ThreadLocalUtil.get(Constant.REQUEST_BODY);
//    }
//
//    public Map<String, Object> getRequestParam() {
//        return ThreadLocalUtil.get(Constant.REQUEST_PARAM);
//    }
//
//    public String getPath() {
//        return ThreadLocalUtil.get(Constant.REQUEST_PATH);
//    }
//
//    public MockRuleBO getMockRule() {
//        return ThreadLocalUtil.get(Constant.MOCK_RULE);
//    }

    public static void remove() {
        ThreadLocalUtil.remove();
    }

//    public Map<String, Object> getData() {
//        Map<String, Object> threadLocal = ThreadLocalUtil.getThreadLocal();
//        return threadLocal;
//    }
}
