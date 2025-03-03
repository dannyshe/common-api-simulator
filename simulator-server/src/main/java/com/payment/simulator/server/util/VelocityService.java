package com.payment.simulator.server.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.payment.simulator.server.bo.MockContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @version 0.0.1
 * @date 2022/04/06
 */
@Component
@Slf4j
public class VelocityService {

    private static Properties props = new Properties();
    private static VelocityEngine velocityEngine;

    static {
        props.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        props.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        props.setProperty(Velocity.RESOURCE_LOADER, "class");
        props.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        // 初始化并取得Velocity引擎
        velocityEngine = new VelocityEngine(props);
    }

    public String assignValue(MockContext mockContext, String template) {
        // 取得velocity的上下文context
        log.info("[assignValue]context:{}", JSONObject.toJSONString(mockContext));
        log.info("[assignValue]response template:{}", template);
        StringWriter stringWriter = new StringWriter();
        VelocityContext context = new VelocityContext();
        JSONObject jsonObject = (JSONObject) JSON.toJSON(mockContext);
        for (Map.Entry entry : jsonObject.entrySet()) {
            context.put((String) entry.getKey(), entry.getValue());
        }
        context.put("now", new Date());
        context.put("date", new DateTool());
        context.put("uuid", UUID.randomUUID().toString());
        velocityEngine.evaluate(context, stringWriter, "", template);
        return String.valueOf(stringWriter);
    }

    public Map<String, Object> assignValue(Map<String, Object> request, String template) {
        // 取得velocity的上下文context
        log.info("[assignValue]request:{}", JSONObject.toJSONString(request));
        log.info("[assignValue]response template:{}", JSONObject.toJSONString(template));
        // 匹配"$!{xxx}"的正则表达式，"?<=":开头，"?=":结尾
        String regx = "(?<=\\$!\\{).*?(?=\\})";
        // 将正则表达式编译为Pattern实例
        Pattern pattern = Pattern.compile(regx);
        // 用该实例匹配模板字符串
        Matcher matcher = pattern.matcher(template);
        // 处理匹配到的结果
        VelocityContext context = new VelocityContext();
        while (matcher.find()) {
            String field = matcher.group();
            if (field.startsWith("NewDate-")) {
                context.put(field, assignDateValue(field));
                log.info("[1]matches field:{}, velocity set value:{}", field, assignDateValue(field));
            } else if (field.startsWith("RandomId-")) {
                context.put(field, assignRandomId(field));
                log.info("[2]matches field:{}, velocity set value:{}", field, assignRandomId(field));
            } else if (request.containsKey(field)) {
                context.put(field, request.get(field));
                log.info("[3]matches field:{}, velocity set value:{}", field, request.get(field));
            } else if (request.toString().contains(field)) {
                String jsonValue = ChannelMockUtils.getJsonValue(JSONObject.toJSONString(request), field);
                context.put(field, jsonValue);
                log.info("[4]matches field:{}, velocity set value:{}", field, jsonValue);
            }
        }

        StringWriter stringWriter = new StringWriter();
        velocityEngine.evaluate(context, stringWriter, "", template);
        return JSON.parseObject(String.valueOf(stringWriter), Map.class);
    }

    public List<Map> assignArrayValue(Map<String, Object> request, String template) {
        // 取得velocity的上下文context
        log.info("[assignArrayValue]request:{}", JSONObject.toJSONString(request));
        log.info("[assignArrayValue]response template:{}", JSONObject.toJSONString(template));
        // 匹配"$!{xxx}"的正则表达式，"?<=":开头，"?=":结尾
        String regx = "(?<=\\$!\\{).*?(?=\\})";
        // 将正则表达式编译为Pattern实例
        Pattern pattern = Pattern.compile(regx);
        // 用该实例匹配模板字符串
        Matcher matcher = pattern.matcher(template);
        // 处理匹配到的结果
        VelocityContext context = new VelocityContext();
        while (matcher.find()) {
            String field = matcher.group();
            if (field.startsWith("NewDate-")) {
                context.put(field, assignDateValue(field));
                log.info("[1]matches field:{}, velocity set value:{}", field, assignDateValue(field));
            } else if (field.startsWith("RandomId-")) {
                context.put(field, assignRandomId(field));
                log.info("[2]matches field:{}, velocity set value:{}", field, assignRandomId(field));
            } else if (request.containsKey(field)) {
                context.put(field, request.get(field));
                log.info("[3]matches field:{}, velocity set value:{}", field, request.get(field));
            } else if (request.toString().contains(field)) {
                String jsonValue = ChannelMockUtils.getJsonValue(JSONObject.toJSONString(request), field);
                context.put(field, jsonValue);
                log.info("[4]matches field:{}, velocity set value:{}", field, jsonValue);
            }
        }
        StringWriter stringWriter = new StringWriter();
        velocityEngine.evaluate(context, stringWriter, "", template);
        // log.info("velocity context keys:{}", Arrays.asList(context.internalGetKeys()));
        // log.info("velocity stringWriter:{}", stringWriter.toString());
        return JSON.parseArray(String.valueOf(stringWriter), Map.class);
    }

    public String assignStringValue(Map<String, Object> request, String template) {
        // 取得velocity的上下文context
        log.info("request:{}", request);
        log.info("response template:{}", template);
        // 匹配"$!{xxx}"的正则表达式，"?<=":开头，"?=":结尾
        String regx = "(?<=\\$!\\{).*?(?=\\})";
        // 将正则表达式编译为Pattern实例
        Pattern pattern = Pattern.compile(regx);
        // 用该实例匹配模板字符串
        Matcher matcher = pattern.matcher(template);
        // 处理匹配到的结果
        VelocityContext context = new VelocityContext();
        while (matcher.find()) {
            String field = matcher.group();
            if (request.containsKey(field)) {
                context.put(field, request.get(field));
                log.info("matches field:{}, velocity set value:{}", field, request.get(field));
            }
        }
        StringWriter stringWriter = new StringWriter();
        velocityEngine.evaluate(context, stringWriter, "", template);
        return String.valueOf(stringWriter);
    }

    /**
     * 根据时间模板返回模板格式的当前时间
     * $!{NewDate-YYYY_MM_DD_HH_MM_SS}
     *
     * @param dateTemplate:NewDate_YYYY_MM_DD_HH_MM_SS
     * @return 2021-03-02 09:57:18
     */
    public String assignDateValue(String dateTemplate) {
        String[] split = dateTemplate.split("-");
        String dateFormat = split[1];
        String format = DateFormatEnum.getDescByCode(dateFormat);
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 根据时间模板返回模板格式的当前时间
     * $!{RandomId-15}
     *
     * @param digitTemplate:RandomId-15
     * @return 135754924865248
     */
    public String assignRandomId(String digitTemplate) {
        String[] split = digitTemplate.split("-");
        String digit = split[1];
        return ChannelMockUtils.idBuilder(Integer.parseInt(digit));
    }

}
