package com.payment.simulator.server.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Danny
 * @version 0.0.1
 * @date 2022/04/06
 */
public class RequestParamsToMap {
    public static JSONObject getParameterMap(HttpServletRequest request) {
        Map<String, String[]> properties = request.getParameterMap();//把请求参数封装到Map<String, String[]>中
        JSONObject returnMap = new JSONObject();
        Iterator<Entry<String, String[]>> iter = properties.entrySet().iterator();
        String name = "";
        String value = "";
        while (iter.hasNext()) {
            Entry<String, String[]> entry = iter.next();
            name = entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) { //用于请求参数中有多个相同名称
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();//用于请求参数中请求参数名唯一
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }
    //返回值类型为Map<String, String>
    public static Map<String, String> getParameterStringMap(HttpServletRequest request) {
        Map<String, String[]> properties = request.getParameterMap();//把请求参数封装到Map<String, String[]>中
        Map<String, String> returnMap = new HashMap<String, String>();
        String name = "";
        String value = "";
        for (Map.Entry<String, String[]> entry : properties.entrySet()) {
            name = entry.getKey();
            String[] values = entry.getValue();
            if (null == values) {
                value = "";
            } else if (values.length>1) {
                for (int i = 0; i < values.length; i++) { //用于请求参数中有多个相同名称
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = values[0];//用于请求参数中请求参数名唯一
            }
            returnMap.put(name, value);

        }
        return returnMap;
    }

}