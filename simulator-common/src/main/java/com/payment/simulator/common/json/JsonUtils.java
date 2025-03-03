package com.payment.simulator.common.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.jackson.datatype.VavrModule;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 描述信息
 *
 * @author Danny She
 * @createTime 2021-11-01
 */
@Slf4j
public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .registerModule(new VavrModule());

    public static String toJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("errorCode convert to json error:{}",e.getMessage(), e);
            return "";
        }
    }

    public static <T> T toBean(String json,Class<T> tClass){
        try {
            return mapper.readValue(json,tClass);
        } catch (JsonProcessingException e) {
            log.error("JsonUtils toBean error:{}",e.getMessage(), e);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> List<T> toList(String json,Class<T> tClass){
        JavaType t  = mapper.getTypeFactory().constructParametricType(List.class, tClass);
        try {
            return mapper.readValue(json,t);
        } catch (JsonProcessingException e) {
            log.error("JsonUtils toList error:{}",e.getMessage(), e);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> Map<String,T> toMap(String json){
        try {
            return mapper.readValue(json,Map.class);
        } catch (JsonProcessingException e) {
            log.error("JsonUtils toMaperror:{}",e.getMessage(), e);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



}
