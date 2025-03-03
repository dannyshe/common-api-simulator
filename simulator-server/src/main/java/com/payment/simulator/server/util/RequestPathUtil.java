package com.payment.simulator.server.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;


public class RequestPathUtil {
    public static JSONObject getPathParamFromUri(String uri, String uriPattern) {
        JSONObject mathedValueMap = new JSONObject();
        if (matcherUrl(uri, uriPattern)) {
            mathedValueMap = matcherParamFromUrl(uri, uriPattern);
        }
        return mathedValueMap;
    }

    private static JSONObject matcherParamFromUrl(String uri, String uriPattern) {
        JSONObject rtn = new JSONObject(5);
        String[] uris = uri.trim().split("/");
        String[] urls = uriPattern.trim().split("/");
        for (int i = 0; i < urls.length; i++) {
            if (urls[i].contains("{") && urls[i].contains("}")) {
                rtn.put(urls[i].replace("{", "").replace("}", ""), uris[i]);
            }
        }
        return rtn;
    }

    public static boolean matcherUrl(String uri, String uriPattern) {
        String[] uris = uri.trim().split("/");
        String[] uriPatterns = uriPattern.trim().split("/");
        if (uris.length != uriPatterns.length) {
            return false;
        }
        for (int i = 0; i < uriPatterns.length; i++) {
            if (uriPatterns[i].contains("{") && uriPatterns[i].contains("}")) {
                continue;
            }
            if (!uriPatterns[i].equals(uris[i])) {
                return false;
            }
        }
//        Pattern pattern = Pattern.compile(uriPattern);
//        Matcher matcher = pattern.matcher(uri);
//        return matcher.matches();
        return true;
    }

    public static void main(String[] args) {
//        String s = "{\n" +
//                "    \"request_id\": \"9307c0c4-8e75-4d44-b900-84de486f3ef5\",\n" +
//                "    \"error_type\": \"processing_error\",\n" +
//                "    \"error_codes\": [\n" +
//                "        \"token_invalid\"\n" +
//                "    ],\n" +
//                "    \"reQuestBody\":\"requestBody\"\n" +
//                "}";
//        System.out.println(JSON.parse(s).toString());
//        System.out.println(ObjectId.getString());
        System.out.println(JSONObject.toJSONString(getPathParamFromUri("/abc/222/45", "/abc/{order}/{id}")));
    }
}
