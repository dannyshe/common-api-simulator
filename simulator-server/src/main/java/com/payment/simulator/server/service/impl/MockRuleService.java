package com.payment.simulator.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.payment.simulator.server.bo.CacheRuleBO;
import com.payment.simulator.server.bo.MockContext;
import com.payment.simulator.server.bo.MockRuleBO;
import com.payment.simulator.server.constant.Constant;
import com.payment.simulator.server.engine.GroovyScriptEngine;
import com.payment.simulator.server.entity.HitRule;
import com.payment.simulator.server.entity.OldMockRule;
import com.payment.simulator.server.enums.CacheOptionEnum;
import com.payment.simulator.server.repository.HitRuleRepository;
import com.payment.simulator.server.repository.OldMockRuleRepository;
import com.payment.simulator.server.service.TemplateService;
import com.payment.simulator.server.util.JSONUtil;
import com.payment.simulator.server.util.RequestPathUtil;
import com.payment.simulator.server.util.VelocityService;
import com.payment.simulator.common.exception.PaymentException;
import com.payment.simulator.common.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.payment.simulator.common.exception.ErrorCode.PARAM_ERROR;
import static com.payment.simulator.common.exception.ErrorCode.SERVER_ERROR;
import static com.payment.simulator.common.exception.ErrorCode.VALIDATE_ERROR;

/**
 * 
 * @version 0.0.1
 * @date 2022/03/31
 */
@Slf4j
@Service
public class MockRuleService {

    @Autowired
    private HitRuleRepository hitRuleRepository;
    @Autowired
    private OldMockRuleRepository oldMockRuleRepository;

    @Autowired
    private CacheRuleService cacheRuleService;

    @Autowired
    protected RedisTemplate<String,String> redisTemplate;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private VelocityService velocityService;


    public ResponseEntity mockData(MockContext mockContext) {
        //匹配主mock模板
        HitRule hitRule = queryMatchedMockRuld(mockContext.getChannelId(),
                mockContext.getRequestPath(),
                mockContext.getRequestMethod(),
                mockContext.getContentType());
        if (hitRule == null) {
            throw new PaymentException(VALIDATE_ERROR, "未匹配到mock_rule");
        }
        //todo
//        //匹配缓存模板mock
//        CacheRuleBO cacheRule = cacheRuleService.queryCacheRuleByMockId(hitRule.getId());
//        if (cacheRule != null) {
//            return executeCacheRuleMock(cacheRule, mockContext);
//        }
        //执行Mock规则模板mock
        return executeMainRuleMock(hitRule, mockContext);
    }

    /**
     * 执行超时返回
     *
     * @param statusCode
     * @return
     */
    private ResponseEntity executeTimeoutMock(String statusCode) {
        try {
            String[] timeoutParams = StringUtils.split(statusCode, ".");
            Thread.sleep(Integer.parseInt(timeoutParams[1]) * 1000L);
        } catch (Exception e) {
            log.error("[executeTimeoutMock] parse status code error", e);
            throw new PaymentException(VALIDATE_ERROR, "执行超时规则解析status code异常");
        }
        return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
    }

    /**
     * 执行reqMatch脚本判断结果
     * @param mockRuleBO
     * @param mockContext
     * @return 脚本执行结果，若脚本不存在，默认为true
     */
    private Boolean reqMatchResult(MockRuleBO mockRuleBO,MockContext mockContext){
        if (StringUtils.isEmpty(mockRuleBO.getReqMatchRule())){
            return Boolean.TRUE;
        }
        try {
            return GroovyScriptEngine.executeGroovyScript(mockContext,mockRuleBO.getReqMatchRule());
        }catch (Exception e){
            log.error("match mock rule error", e);
            throw new PaymentException(SERVER_ERROR, "匹配mock rule异常");
        }
    }

    /**
     * 处理path value
     * @param mockRuleBO
     * @param mockContext
     */
    private void dealPathValue(MockRuleBO mockRuleBO,MockContext mockContext){
        if (StringUtils.contains(mockRuleBO.getPath(), "{") && StringUtils.contains(mockRuleBO.getPath(), "}")) {
            mockContext.setPathValueMap(RequestPathUtil.getPathParamFromUri(mockContext.getRequestPath(), mockRuleBO.getPath()));
        }
    }

    /**
     * 主rule渲染
     *
     * @param hitRule
     * @param mockContext
     * @return
     */
    private ResponseEntity executeMainRuleMock(HitRule hitRule,
                                               MockContext mockContext) {
        //超时状态优先mock
        if (StringUtils.contains(hitRule.getResponseCode(), Constant.STATUS_CODE_TIMEOUT)) {
            return executeTimeoutMock(hitRule.getResponseCode());
        }
        String response = velocityService.assignValue(mockContext,
                hitRule.getResponse());
        return new ResponseEntity<>(response,
                HttpStatus.valueOf(Integer.parseInt(hitRule.getResponseCode())));
    }

    /**
     * 缓存rule渲染
     *
     * @param cacheRule
     * @param mockContext
     * @return
     */
    private ResponseEntity executeCacheRuleMock(CacheRuleBO cacheRule, MockContext mockContext) {
        //获取key
        String cacheKey = getCacheKey(mockContext, cacheRule);
        String data = redisTemplate.boundValueOps(cacheKey).get();
        //所有操作前均先查一下cacheBody
        if (StringUtils.isNotEmpty(data)) {
            mockContext.setCacheBody(JSON.parseObject(data));
        }
        //判断是否有要执行的缓存判断脚本
        if (StringUtils.isNotEmpty(cacheRule.getCacheBodyMatchRule())) {
            try {
                Boolean result =GroovyScriptEngine.executeGroovyScript(mockContext,cacheRule.getCacheBodyMatchRule());
                if (Boolean.FALSE.equals(result)) {
                    //优先执行超时任务
                    return new ResponseEntity<>(velocityService.assignValue(mockContext, cacheRule.getMatchErrorResponseTemplate()), HttpStatus.valueOf(Integer.valueOf(cacheRule.getMatchErrorStatusCode())));
                }
            } catch (Exception e) {
                log.error("[MockRuleServiceImpl] excuteCacheRuleMock error", e);
                throw new PaymentException(SERVER_ERROR, "执行缓存规则mock异常");
            }
        }
        //无判断脚本或者脚本match通过
        String template = null;
        String statusCode = null;
        //put操作
        if (CacheOptionEnum.PUT.equals(CacheOptionEnum.getOptionName(cacheRule.getCacheOption()))) {
            //put方法不执行后面的模板操作
            redisTemplate.boundValueOps(cacheKey).set(velocityService.assignValue(mockContext, cacheRule.getCacheBody()), cacheRule.getCacheTime(), TimeUnit.SECONDS);
            template = cacheRule.getResponseTemplate();
            statusCode = cacheRule.getMatchStatusCode();
        }
        //get根据数据判断是response模板还是null_response模板
        if (CacheOptionEnum.GET.equals(CacheOptionEnum.getOptionName(cacheRule.getCacheOption()))) {
            if (data == null) {
                template = cacheRule.getNullResponseTemplate();
                statusCode = cacheRule.getNullMatchStatusCode();
            } else {
                template = cacheRule.getResponseTemplate();
                statusCode = cacheRule.getMatchStatusCode();
            }
        }
        //优先执行超时任务
        if (StringUtils.contains(statusCode, Constant.STATUS_CODE_TIMEOUT)) {
            return executeTimeoutMock(statusCode);
        }
        if (StringUtils.isEmpty(template)){
            return new ResponseEntity<>(HttpStatus.valueOf(Integer.parseInt(statusCode)));
        }
        return new ResponseEntity<>(velocityService.assignValue(mockContext, template), HttpStatus.valueOf(Integer.valueOf(statusCode)));
    }

    private String getCacheKey(MockContext mockContext, CacheRuleBO cacheRuleBO) {
        StringBuilder keyStringBuffer = new StringBuilder();
        List<String> reqCacheRules = JSON.parseArray(cacheRuleBO.getReqCacheRule(), String.class);
        for (String reqCacheRule : reqCacheRules) {
            keyStringBuffer.append(JSONUtil.getDataByPath((JSONObject) JSON.toJSON(mockContext), reqCacheRule));
            keyStringBuffer.append("_");
        }
        return keyStringBuffer.toString();
    }

    /**
     * 查询匹配的mockRule集合，将脚本匹配规则为null的放在最下面
     *
     * @param channelId
     * @param requestUrl
     * @param requestMethod
     * @param contentType
     * @return
     */
    public HitRule queryMatchedMockRuld(String channelId, String requestUrl, String requestMethod, String contentType) {
        List<HitRule> hitRules =
                hitRuleRepository.findByChannelIdAndPathAndRequestMethodAndContentType(channelId, requestUrl, requestMethod,
                contentType);

//        if (CollectionUtils.isEmpty(mockRules)) {
//            mockRules = mockRuleMapper.queryMockRegPathRule(channelId, requestMethod, contentType);
//            if (CollectionUtils.isNotEmpty(mockRules)) {
//                mockRules = mockRules.stream().filter(d -> RequestPathUtil.matcherUrl(requestUrl, d.getPath())).collect(Collectors.toList());
//            }
//        }
        if (CollectionUtils.isEmpty(hitRules)) {
            throw new PaymentException(VALIDATE_ERROR, "未查询到hit_rule规则配置");
        }
//        Optional<OldMockRule> nullMockRule = oldMockRules.stream().filter(oldMockRule -> StringUtils.isEmpty(oldMockRule.getReqMatchRule())).findFirst();
//        //reqMatchRule为空的放在最下面
//        if (nullMockRule.isPresent()){
//            oldMockRules.remove(nullMockRule.get());
//            oldMockRules.add(nullMockRule.get());
//        }
//        return oldMockRules.stream().map(oldMockRule -> {
//            oldMockRule.setReqMatchRule(StringUtils.defaultIfBlank(oldMockRule.getReqMatchRule(), null));
//            return BeanUtils.copyProperties(oldMockRule, MockRuleBO.class);
//        }).collect(Collectors.toList());
        return hitRules.get(0);
    }


    public OldMockRule insertMockRule(OldMockRule oldMockRule) {
        oldMockRule.setId(UUID.randomUUID().toString());
        oldMockRule.setCreated(new Date());
        return oldMockRuleRepository.save(oldMockRule);
    }

//    public BasePaginationResponse<MockRuleResponse> queryMockRules(MockRuleQuery mockRuleQuery) {
//        Integer count = mockRuleMapper.queryCount(mockRuleQuery);
//        BasePaginationResponse<MockRuleResponse> pagenationResponse = new BasePaginationResponse<>(mockRuleQuery.getPageNumber(), mockRuleQuery.getPageSize(), count, null);
//        if (count > 0) {
//            pagenationResponse.setItems(BeanUtils.copyListProperties(mockRuleMapper.query(mockRuleQuery), MockRuleResponse.class));
//        }
//        return pagenationResponse;
//    }

    public void updateMockRule(OldMockRule oldMockRule) {
        if (StringUtils.isEmpty(oldMockRule.getId())) {
            throw new PaymentException(PARAM_ERROR, "id是必填的");
        }
        oldMockRule.setUpdated(new Date());
        oldMockRuleRepository.save(oldMockRule);
    }

    public void delteMockRule(String id) {
        oldMockRuleRepository.deleteById(id);
    }
}
