package com.payment.simulator.server.dto;

import com.alibaba.fastjson.JSONObject;
import com.payment.simulator.server.constant.Constant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimulateContext {

    private String path;

    private String queryString;

    private JSONObject headers = new JSONObject();

    private JSONObject params = new JSONObject();

    private JSONObject body = new JSONObject();

    private JSONObject cacheBody = new JSONObject();

    private JSONObject pathValueMap = new JSONObject();

    private JSONObject extraData = new JSONObject();

    public void setChannelId(String channelId) {
        headers.put(Constant.CHANNEL_ID, channelId);
    }

    public void setContentType(String contentType) {
        headers.put(Constant.CONTENT_TYPE, contentType);
    }

    public void setRequestMethod(String requestMethod) {
        headers.put(Constant.REQUEST_METHOD, requestMethod);
    }

    public String getChannelId() {
        return headers.getString(Constant.CHANNEL_ID);
    }

    public String getContentType() {
        return headers.getString(Constant.CONTENT_TYPE);
    }

    public String getRequestMethod() {
        return headers.getString(Constant.REQUEST_METHOD);
    }
}
