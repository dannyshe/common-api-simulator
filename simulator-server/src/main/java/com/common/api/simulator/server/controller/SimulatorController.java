package com.common.api.simulator.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.api.simulator.server.dto.SimulateContext;
import com.common.api.simulator.server.service.SimulateService;
import com.common.api.simulator.server.util.BodyReaderHttpServletRequestWrapper;
import com.common.api.simulator.server.util.RequestParamsToMap;
import com.common.api.simulator.server.util.XmlTool;
import com.common.api.simulator.common.exception.SimulateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.common.api.simulator.common.exception.ErrorCode.VALIDATE_ERROR;

@RestController
@Slf4j
public class SimulatorController {
    @Autowired
    private SimulateService simulateService;

    private static final String DEFAULT_REQUEST_URI = "/";


    @RequestMapping("/**")
    public ResponseEntity simulate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SimulateContext simulateContext = parseRequest(request);
        ResponseEntity responseEntity = simulateService.execute(simulateContext);
        return responseEntity;
    }

    private SimulateContext parseRequest(HttpServletRequest request) throws IOException, DocumentException {

        String channelId = request.getHeader("channel_id");
        if (StringUtils.isEmpty(channelId)) {
            throw new SimulateException(VALIDATE_ERROR, "channel_id can't be empty in header");
        }
        String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
        if (StringUtils.isEmpty(contentType)) {
            throw new SimulateException(VALIDATE_ERROR, "contentType can't be empty in header");
        }
        String requestURI = request.getRequestURI();
        if (StringUtils.isEmpty(contentType)) {
            requestURI = DEFAULT_REQUEST_URI;
        }
        SimulateContext simulateContext = new SimulateContext();
        simulateContext.setChannelId(channelId.toLowerCase());
        simulateContext.setRequestMethod(request.getMethod().toUpperCase());
        simulateContext.setContentType(contentType.toLowerCase());
        simulateContext.setPath(requestURI);
        simulateContext.setQueryString(request.getQueryString());
        simulateContext.setParams(RequestParamsToMap.getParameterMap(request));
        String bodyMessage = new BodyReaderHttpServletRequestWrapper(request).getBody();
        JSONObject requestBody = new JSONObject();
        if (StringUtils.isNotEmpty(bodyMessage)) {
            if (StringUtils.contains(request.getHeader(HttpHeaders.CONTENT_TYPE), MediaType.APPLICATION_XML_VALUE)) {
                requestBody = XmlTool.documentToJSONObject(bodyMessage);
            } else if (StringUtils.contains(request.getHeader(HttpHeaders.CONTENT_TYPE), MediaType.APPLICATION_JSON_VALUE)) {
                requestBody = JSONObject.parseObject(bodyMessage);
            }
        }
        simulateContext.setBody(requestBody);

        return simulateContext;

    }
}
