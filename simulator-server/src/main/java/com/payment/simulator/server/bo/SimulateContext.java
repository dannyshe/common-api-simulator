package com.payment.simulator.server.bo;

import com.alibaba.fastjson.JSONObject;
import com.payment.simulator.server.constant.Constant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimulateContext {

    private String requestPath;

    private String requestQueryString;

    private JSONObject requestHeader = new JSONObject();

    private JSONObject requestParam = new JSONObject();

    private JSONObject requestBody = new JSONObject();

    private JSONObject cacheBody = new JSONObject();

    private JSONObject pathValueMap = new JSONObject();

    private JSONObject extraData = new JSONObject();

    public void setChannelId(String channelId) {
        requestHeader.put(Constant.CHANNEL_ID, channelId);
    }

    public void setContentType(String contentType) {
        requestHeader.put(Constant.CONTENT_TYPE, contentType);
    }

    public void setRequestMethod(String requestMethod) {
        requestHeader.put(Constant.REQUEST_METHOD, requestMethod);
    }

    public String getChannelId() {
        return requestHeader.getString(Constant.CHANNEL_ID);
    }

    public String getContentType() {
        return requestHeader.getString(Constant.CONTENT_TYPE);
    }

    public String getRequestMethod() {
        return requestHeader.getString(Constant.REQUEST_METHOD);
    }
}
