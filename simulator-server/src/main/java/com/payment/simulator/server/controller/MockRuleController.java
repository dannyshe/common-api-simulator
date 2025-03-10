package com.payment.simulator.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.payment.simulator.common.utils.BeanUtils;
import com.payment.simulator.server.bo.MockContext;
import com.payment.simulator.server.bo.request.MockRuleRequest;
import com.payment.simulator.server.entity.OldMockRule;
import com.payment.simulator.server.service.impl.MockRuleService;
import com.payment.simulator.server.util.BodyReaderHttpServletRequestWrapper;
import com.payment.simulator.server.util.RequestParamsToMap;
import com.payment.simulator.server.util.XmlTool;
import com.payment.simulator.common.dto.response.GenericResponse;
import com.payment.simulator.common.exception.PaymentException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.payment.simulator.common.exception.ErrorCode.VALIDATE_ERROR;

/**
 * @description:
 * 
 **/
@RestController
@Slf4j
public class MockRuleController {
    @Autowired
    private MockRuleService mockRuleServiceo;


    @RequestMapping("/**")
    public ResponseEntity doMock(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //校验解析请求数据
        MockContext mockContext = checkAndParseRequest(request);
        ResponseEntity responseEntity = mockRuleServiceo.mockData(mockContext);
        return responseEntity;
    }

    @RequestMapping("/v1/api/mock/data")
//    @LogAnnotation(name = "通用mock 数据接口", detailLogType = LogNameConstants.FACADE_INFO)
    public ResponseEntity mock(@RequestBody MockContext mockContext) throws Exception {
        ResponseEntity responseEntity = mockRuleServiceo.mockData(mockContext);
        return responseEntity;
    }

    /**
     * 解析request
     *
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    private MockContext checkAndParseRequest(HttpServletRequest request) throws IOException, DocumentException {

        String channelId = request.getHeader("channel_id");
        if (StringUtils.isEmpty(channelId)) {
            throw new PaymentException(VALIDATE_ERROR, "header中channel_id不能为空");
        }
        MockContext mockContext = new MockContext();
        mockContext.setChannelId(channelId);
        mockContext.setRequestMethod(request.getMethod());
        mockContext.setContentType(request.getHeader(HttpHeaders.CONTENT_TYPE));
        mockContext.setRequestPath(request.getRequestURI());
        mockContext.setRequestParam(RequestParamsToMap.getParameterMap(request));
        String bodyMessage = new BodyReaderHttpServletRequestWrapper(request).getBody();
        JSONObject requestBody = new JSONObject();
        if (StringUtils.contains(request.getHeader(HttpHeaders.CONTENT_TYPE), MediaType.APPLICATION_XML_VALUE)) {
            requestBody = XmlTool.documentToJSONObject(bodyMessage);
        } else if (StringUtils.contains(request.getHeader(HttpHeaders.CONTENT_TYPE), MediaType.APPLICATION_JSON_VALUE)) {
            requestBody = JSONObject.parseObject(bodyMessage);
        }
        mockContext.setRequestBody(requestBody);

        return mockContext;

    }

//    @PostMapping("/v1/api/mock_rule/query")
//    public GenericResponse<BasePaginationResponse<MockRuleResponse>> queryMockRule(
//            @RequestBody MockRuleRequest request) {
//        MockRuleQuery mockRuleQuery = BeanUtils.copyProperties(request, MockRuleQuery.class);
//        return GenericResponse.success(mockRuleServiceo.queryMockRules(mockRuleQuery));
//    }

    @PostMapping("/v1/api/mock_rule/create")
    public GenericResponse createMockRule(
            @RequestBody MockRuleRequest request) {
        OldMockRule oldMockRule = BeanUtils.copyProperties(request, OldMockRule.class);
        return GenericResponse.success(mockRuleServiceo.insertMockRule(oldMockRule));
    }

    @PostMapping("/v1/api/mock_rule/update")
    public GenericResponse updateMockRule(
            @RequestBody MockRuleRequest request) {
        OldMockRule oldMockRule = BeanUtils.copyProperties(request, OldMockRule.class);
        mockRuleServiceo.updateMockRule(oldMockRule);
        return GenericResponse.success();
    }

    @PostMapping("/v1/api/mock_rule/delete")
    public GenericResponse deleteMockRule(
            @RequestBody MockRuleRequest request) {
        OldMockRule oldMockRule = BeanUtils.copyProperties(request, OldMockRule.class);
        mockRuleServiceo.delteMockRule(oldMockRule.getId());
        return GenericResponse.success();
    }

}
