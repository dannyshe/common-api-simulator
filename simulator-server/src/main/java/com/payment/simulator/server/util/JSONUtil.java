package com.payment.simulator.server.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @version 1.0
 * @date 2022/04/07
 */
public class JSONUtil {

    private static Pattern pattern = Pattern.compile("\\[.\\]");

    public static String getDataByPath(JSONObject object, String path) {
        return splitDataByPath(object, path, new ArrayList<>()).get(0);
    }


    private static List<String> splitDataByPath(JSONObject object, String path, List<String> list) {
        if (path == null || "".equals(path)) {
            System.out.println("path 不能为空");
        }
        if (object == null) {
            return list;
        }
        int poz = path.indexOf(".");
        if (poz != -1) {
            String p = path.substring(0, poz);
            String idx = regexPath(p);
            //继续封装，递归
            String nextPath = path.substring(poz + 1);
            if (idx == null) {
                splitDataByPath(object.getJSONObject(p), nextPath, list);
            } else {
                JSONArray array = object.getJSONArray(p.substring(0, p.length() - 3));
                if ("?".equals(idx)) {
                    //everyone should to do
                    for (Object o : array) {
                        splitDataByPath((JSONObject) o, nextPath, list);
                    }
                } else {
                    //just get one
                    int index = Integer.valueOf(idx);
                    //防止下标越界
                    if (index < array.size()) {
                        splitDataByPath((JSONObject) array.get(index), nextPath, list);
                    }

                }
            }
        } else {
            String idx = regexPath(path);
            //取数据，封装返回
            if (idx == null) {
                list.add(object.getString(path));
            } else {
                JSONArray array = object.getJSONArray(path.substring(0, path.length() - 3));
                if ("?".equals(idx)) {
                    //everyone should to do
                    for (Object o : array) {
                        list.add(o.toString());
                    }
                } else {
                    //just get one
                    int index = Integer.valueOf(idx);
                    if (index < array.size()) {
                        list.add(array.getString(index));
                    }
                }
            }
        }
        return list;
    }

    /**
     * 解析path，并判断是否为数组，及数组中的位置
     *
     * @param path
     * @return
     */
    private static String regexPath(String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            String pos = matcher.group();
            return pos.substring(1, 2);
        }
        return null;
    }
}