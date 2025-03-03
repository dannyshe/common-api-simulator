package com.payment.simulator.server.entity;

import lombok.Data;

import java.util.Date;

/**
 * 
 * @version v1.0
 * @date 2022/4/7
 **/
@Data
public class MockRule {

    /**
     * id
     */
    private String id;

    /**
     * 渠道
     */
    private String channelId;


    /**
     * 请求路径
     */
    private String path;

    /**
     * 请求方法POST、GET、UPDATE
     */
    private String requestMethod;
    /**
     * 请求格式
     */
    private String contentType;

    /**
     * 特定逻辑模版code
     */
    private String templateCode;


    /**
     * 入参正则表达式、适用非json
     */
    private String reqRegRule;
    /**
     * 入参json路径，适用json
     */
    private String reqJsonPath;
    /**
     * 匹配入参值规则
     */
    private String reqMatchRule;
    /**
     * 回参code
     */
    private String statusCode;
    /**
     * 回参模版，根据模版生成对应回参
     */
    private String responseTemplate;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 返回时间
     */
    private Date updated;
    /**
     * 备注
     */
    private String remark;
}
