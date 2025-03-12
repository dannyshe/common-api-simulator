package com.payment.simulator.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 
 * @version 1.0
 * @description 加密前key排序
 * @date 2022/1/4
 */
@Slf4j
public class MapUtil {

    /**
     * 加密前key排序
     * @param data 排序map
     * @return
     */
    public static String getPreSignData(Map<String, String> data) {

        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(data.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> arg0, Map.Entry<String, String> arg1) {
                return (arg0.getKey()).compareTo(arg1.getKey());
            }
        });
        String preSignStr = "";

        for (Map.Entry<String, String> entry : infoIds) {
            preSignStr += entry.getKey();
            preSignStr += "=";
            preSignStr += entry.getValue();
            preSignStr += "&";
        }
        preSignStr = preSignStr.substring(0, preSignStr.length() - 1);
        return preSignStr;
    }
}