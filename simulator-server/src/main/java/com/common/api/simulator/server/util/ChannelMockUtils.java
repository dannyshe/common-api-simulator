package com.common.api.simulator.server.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
public class ChannelMockUtils {

    public static char RandomPos() {
        char[] Symbol = {'`', '!', '"', '?', '$', '?', '%', '^', '&', '*', '(', ')',
                '_', '-', '+', '=', '{', '[', '}', ']', ':', ';', '@', '\\', '~', '#', '|', '<', ',', '>', '.', '?', '/'};
        char[] Number = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        char[] Lowercase = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] Capitalization = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        //Object[] AnyChar = { Symbol , Number , Lowercase , Capitalization };
        //Object[] AnyChar = {Number, Lowercase};
        Object[] AnyChar = {Number};
        char[] currentChar = (char[]) AnyChar[new Random().nextInt(AnyChar.length)];
        int pos = new Random().nextInt(currentChar.length);
        int i = 0;
        for (char c : currentChar) {
            if (i++ == pos) {
                return c;
            }
        }
        return 0;
    }

    public static String idBuilder(int digit) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digit; i++) {
            sb.append(RandomPos());
        }
        return sb.toString();
    }

    public static String getJsonValue(String jsonString, String key) {
        String js = JSONObject.toJSONString(jsonString);
        List<String> collect = Arrays.stream(js.split(",")).filter(s -> s.contains(key)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            return null;
        }
        String[] kvs = collect.get(0).replaceAll("[\\\\\"]", "").split(":");
        String value = kvs[kvs.length - 1];
        log.info("[getJsonValue]key:{},value:{}", key, value);
        return value;
    }


}
