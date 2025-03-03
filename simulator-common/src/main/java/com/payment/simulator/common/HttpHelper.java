package com.payment.simulator.common;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * 描述信息
 *
 * @author Grayson
 * @createTime 2021-11-01
 */
@Slf4j
public class HttpHelper {


    private HttpHelper() {
        throw new IllegalStateException("HttpHelper class");
    }

    /**
     * 获取请求Body
     * @param request http输入
     * @return 返回post的内容
     */
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = request.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("getBodyString error", e);
        }
        return sb.toString();
    }

    /**
     * 符号处理
     * @param content
     * @return
     */
    public static String symbolProcess(String content){
        if (StringUtils.isNotBlank(content)) {
            Pattern p = Pattern.compile("\t|\r|\n|\\s*");
            Matcher m = p.matcher(content);
            content = m.replaceAll("");
        }
        return content;

    }
}
