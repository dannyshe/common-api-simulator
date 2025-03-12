package com.payment.simulator.server.service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.payment.simulator.server.dto.SimulateContext;
import com.payment.simulator.server.constant.Constant;
import com.payment.simulator.server.entity.HitRule;
import com.payment.simulator.server.enums.ActionTypeEnum;
import com.payment.simulator.server.repository.HitRuleRepository;
import com.payment.simulator.server.util.VelocityService;
import com.payment.simulator.common.exception.SimulateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.payment.simulator.common.exception.ErrorCode.VALIDATE_ERROR;

@Slf4j
@Service
public class SimulateService {

    @Autowired
    private HitRuleRepository hitRuleRepository;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private VelocityService velocityService;
    private final static String DEFAULT_ERROR_RESPONSE = "Error Response Code Matched.";


    public ResponseEntity execute(SimulateContext simulateContext) {
        //todo - update the request url if necessary
        HitRule hitRule = findRule(simulateContext.getChannelId(), simulateContext.getPath(),
                simulateContext.getRequestMethod(), simulateContext.getContentType());
        if (hitRule == null) {
            String errorMessage = String.format("hit_rule not found for channel id '%s' content type '%s' http method '%s'",
                    simulateContext.getChannelId(), simulateContext.getContentType(),
                    simulateContext.getRequestMethod());
            throw new SimulateException(VALIDATE_ERROR, errorMessage);
        }
        return handleSimulateLogic(hitRule, simulateContext);
    }

    private void handleTimeout(String statusCode) {
        try {
            String[] timeoutParams = StringUtils.split(statusCode, ".");
            Thread.sleep(Integer.parseInt(timeoutParams[1]) * 1000L);
        } catch (Exception e) {
            log.error("[handleTimeout] parse status code error", e);
            throw new SimulateException(VALIDATE_ERROR, "handleTimeout meet error");
        }
    }

    private ResponseEntity handleSimulateLogic(HitRule hitRule, SimulateContext simulateContext) {
        handlePathVariable(hitRule.getPathVariableRule(), simulateContext);
        String responseCode = hitRule.getResponseCode();
        if (responseCode.contains("|")) {
            List<String> codes = Arrays.stream(responseCode.split("\\|")).toList();
            responseCode = codes.get(codes.size() - 1);
            String actionCode = codes.get(0);
            if (actionCode.contains(Constant.RESPONSE_CODE_TIMEOUT)) {
                //handle timeout response code
                handleTimeout(actionCode);
            }
        }
        if (!responseCode.equals("200") && !responseCode.equals("201")) {
            //handle abnormal response code
            try {
                int statusCode = Integer.parseInt(responseCode);
                HttpStatus httpStatus = HttpStatus.resolve(statusCode);
                if (httpStatus != null) {
                    return ResponseEntity.status(httpStatus).body(DEFAULT_ERROR_RESPONSE);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Invalid status code: " + responseCode);
                }
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Invalid status code format: " + responseCode);
            }
        }

        ActionTypeEnum actionType = ActionTypeEnum.fromString(hitRule.getActionType());
        switch (actionType) {
            case ASSEMBLE_ONLY -> {
                //default logic
                return ResponseEntity
                        .status(HttpStatus.valueOf(Integer.parseInt(responseCode)))
                        .contentType(MediaType.valueOf(hitRule.getContentType()))
                        .body(velocityService.assignValue(simulateContext, hitRule.getResponse(), null));
            }
            case ASSEMBLE_AND_CACHE -> {
                return ResponseEntity
                        .status(HttpStatus.valueOf(Integer.parseInt(responseCode)))
                        .contentType(MediaType.valueOf(hitRule.getContentType()))
                        .body(cacheService.assembleAndSave(simulateContext, hitRule));
            }
            case QUERY_CACHE -> {
                //query from cache
                return ResponseEntity
                        .status(HttpStatus.valueOf(Integer.parseInt(responseCode)))
                        .contentType(MediaType.valueOf(hitRule.getContentType()))
                        .body(cacheService.query(simulateContext, hitRule));
            }
            case UPDATE_CACHE -> {
                //delete record from cache
                return ResponseEntity
                        .status(HttpStatus.valueOf(Integer.parseInt(responseCode)))
                        .contentType(MediaType.valueOf(hitRule.getContentType()))
                        .body(cacheService.update(simulateContext, hitRule));
            }
            case DELETE_CACHE -> {
                //delete cache
                return ResponseEntity
                        .status(HttpStatus.valueOf(Integer.parseInt(responseCode)))
                        .contentType(MediaType.valueOf(hitRule.getContentType()))
                        .body(cacheService.delete(simulateContext, hitRule));
            }
            default ->  throw new SimulateException(VALIDATE_ERROR, "Invalid action type: " + actionType);
        }
    }

    private void handlePathVariable(String pathVariableRule, SimulateContext simulateContext) {
        if (StringUtils.isBlank(pathVariableRule)) {
            return;
        }

        try {
            JSONObject rule = JSON.parseObject(pathVariableRule);
            String regexp = rule.getString("regexp");
            List<String> keys = rule.getJSONArray("keys").toJavaList(String.class);

            // 将正则表达式中的 .* 替换为捕获组
            String regexPattern = regexp.replace(".*", "([^/]+)");

            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regexPattern);
            java.util.regex.Matcher matcher = pattern.matcher(simulateContext.getPath());

            if (matcher.matches()) {
                // 从第一个捕获组开始（group(0)是整个匹配）
                for (int i = 0; i < keys.size(); i++) {
                    String value = matcher.group(i + 1);
                    if (StringUtils.isNotBlank(value)) {
                        simulateContext.getParams().put(keys.get(i), value);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to handle pathVariableRule: {}, path: {}, error: {},",
                    pathVariableRule, simulateContext.getPath(), e.getMessage(), e);
        }
    }

    public HitRule findRule(String channelId, String requestUrl, String requestMethod, String contentType) {
        List<HitRule> hitRules = hitRuleRepository.findByChannelIdAndRequestMethodAndContentType(
                channelId, requestMethod, contentType);
        if (CollectionUtils.isEmpty(hitRules)) {
            throw new SimulateException(VALIDATE_ERROR, "未查询到hit_rule规则配置");
        }
        return hitRules.stream().filter( rule ->
                rule.getPath().equals(requestUrl) || Pattern.compile(rule.getPath()).matcher(requestUrl).matches()
        ).findFirst().orElse(null);
    }
}
