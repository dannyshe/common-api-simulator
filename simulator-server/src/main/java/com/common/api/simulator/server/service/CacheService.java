package com.common.api.simulator.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.api.simulator.common.exception.SimulateException;
import com.common.api.simulator.server.dto.SimulateContext;
import com.common.api.simulator.server.engine.GroovyScriptEngine;
import com.common.api.simulator.server.entity.*;
import com.common.api.simulator.server.repository.DeleteActionRepository;
import com.common.api.simulator.server.repository.QueryCacheActionRepository;
import com.common.api.simulator.server.repository.SaveActionRepository;
import com.common.api.simulator.server.repository.UpdateActionRepository;
import com.common.api.simulator.server.util.VelocityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.common.api.simulator.common.exception.ErrorCode.SERVER_ERROR;

/**
 * 
 * @version 0.0.1
 * @date 2022/03/31
 */
@Slf4j
@Service
public class CacheService {

    @Autowired
    private VelocityService velocityService;

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    @Autowired
    private QueryCacheActionRepository queryCacheActionRepository;

    @Autowired
    private DeleteActionRepository deleteActionRepository;

    @Autowired
    private UpdateActionRepository updateActionRepository;

    @Autowired
    private SaveActionRepository saveActionRepository;

//    @Override
//    public BasePaginationResponse<CacheRuleResponse> queryCacheRules(CacheRuleQuery cacheRuleQuery) {
//        Integer count = cacheRuleMapper.queryCount(cacheRuleQuery);
//        BasePaginationResponse<CacheRuleResponse> pagenationResponse = new BasePaginationResponse<>(cacheRuleQuery.getPageNumber(), cacheRuleQuery.getPageSize(), count, null);
//        if (count > 0) {
//            pagenationResponse.setItems(BeanUtils.copyListProperties(cacheRuleMapper.query(cacheRuleQuery), CacheRuleResponse.class));
//        }
//        return pagenationResponse;
//    }

    public String assembleAndSave(SimulateContext simulateContext, HitRule hitRule) {
        SaveAction saveAction = saveActionRepository.findById(hitRule.getActionId()).get();

        String objectId = GroovyScriptEngine.generateObjectId(simulateContext, saveAction.getGenerateKeyScript());
        String response = velocityService.assignValue(simulateContext, hitRule.getResponse(), objectId);
        if (StringUtils.isNotEmpty(saveAction.getGenerateKeyScript()) && saveAction.getCacheTtlHours() > 0) {
            if (!StringUtils.isEmpty(objectId)) {
                redisTemplate.boundValueOps(objectId).set(response, saveAction.getCacheTtlHours(), TimeUnit.HOURS);
            }
        }
        return response;
    }

    public String delete(SimulateContext simulateContext, HitRule hitRule) {
        DeleteAction deleteAction = deleteActionRepository.findById(hitRule.getActionId()).get();
        if (deleteAction == null) {
            throw new SimulateException(SERVER_ERROR, "DeleteAction not found with id: " +  hitRule.getActionId());
        }
        String objectId = GroovyScriptEngine.generateObjectId(simulateContext, deleteAction.getDeleteKeyScript());
        if(redisTemplate.delete(objectId)){
            return velocityService.assignValue(simulateContext, deleteAction.getResponse(), null);
        }else{
            return velocityService.assignValue(simulateContext, deleteAction.getNotfoundResponse(), null);
        }
    }

    public String update(SimulateContext simulateContext, HitRule hitRule) {
        UpdateAction updateAction = updateActionRepository.findById(hitRule.getActionId()).get();
        if (updateAction == null) {
            throw new SimulateException(SERVER_ERROR, "UpdateAction not found with id: " +  hitRule.getActionId());
        }
        String objectId = GroovyScriptEngine.generateObjectId(simulateContext, updateAction.getUpdateKeyScript());
        String response = redisTemplate.boundValueOps(objectId).get();
        if(StringUtils.isEmpty(response)){
            return velocityService.assignValue(simulateContext, updateAction.getNotfoundResponse(), null);
        }
        
        //update
        if(simulateContext.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)){
            redisTemplate.boundValueOps(objectId).set(getUpdatedJson(response, updateAction.getUpdateRule()));
        }
        if(simulateContext.getContentType().equals(MediaType.APPLICATION_XML_VALUE)){
            redisTemplate.boundValueOps(objectId).set(getUpdatedXml(response, updateAction.getUpdateRule()));
        }

        return velocityService.assignValue(simulateContext, updateAction.getResponse(), null);
    }

    private String getUpdatedXml(String response, String updateRule) {
        if (StringUtils.isBlank(response) || StringUtils.isBlank(updateRule)) {
            return response;
        }

        try {
            JSONObject rule = JSON.parseObject(updateRule);
            String targetNode = "//" + rule.getString("targetNode").replace(".", "/");
            String targetValue = rule.getString("targetValue");

            Document document = DocumentHelper.parseText(response);
            List<Node> nodes = document.selectNodes(targetNode);
            for (Node node : nodes) {
                node.setText(targetValue);
            }

            return document.asXML();
        } catch (Exception e) {
            log.error("Failed to update XML", e);
            return response;
        }
    }

    private String getUpdatedJson(String response, String updateRule) {
        if (StringUtils.isBlank(response) || StringUtils.isBlank(updateRule)) {
            return response;
        }

        try {
            JSONObject jsonResponse = JSON.parseObject(response);
            JSONObject rule = JSON.parseObject(updateRule);
            String[] paths = rule.getString("targetNode").split("\\.");
            String targetValue = rule.getString("targetValue");

            JSONObject current = jsonResponse;
            for (int i = 0; i < paths.length - 1; i++) {
                current = current.getJSONObject(paths[i]);
                if (current == null) {
                    return response;
                }
            }

            current.put(paths[paths.length - 1], targetValue);
            return jsonResponse.toJSONString();
        } catch (Exception e) {
            log.error("Failed to update JSON", e);
            return response;
        }
    }

    public Object query(SimulateContext simulateContext, HitRule hitRule) {
        QueryCacheAction queryCacheAction = queryCacheActionRepository.findById(hitRule.getActionId()).get();
        if (queryCacheAction == null) {
            throw new SimulateException(SERVER_ERROR, "QueryCacheAction not found with id: " +  hitRule.getActionId());
        }
        String objectId = GroovyScriptEngine.generateObjectId(simulateContext, queryCacheAction.getQueryKeyScript());
        String response = redisTemplate.boundValueOps(objectId).get();
        if(StringUtils.isEmpty(response)){
            return velocityService.assignValue(simulateContext, queryCacheAction.getNotfoundResponse(), null);
        }

        return response;
    }
}
