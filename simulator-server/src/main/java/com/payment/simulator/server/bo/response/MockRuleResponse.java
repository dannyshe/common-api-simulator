package com.payment.simulator.server.bo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @version v1.0
 * @date 2022/4/7
 **/
@Data
public class MockRuleResponse {


    /**
     * id
     */
    @ApiModelProperty(value = "id", example = "主键")
    private String id;

    /**
     * 渠道
     */
    private String channelId;


    /**
     * 请求路径
     */
    @ApiModelProperty(value = "路径", example = "/payments")
    private String path;


    /**
     * 请求格式
     */
    @ApiModelProperty(value = "请求格式", example = "application/json")
    private String contentType;


    /**
     * 特定逻辑模版code
     */
    @ApiModelProperty(value = "特定逻辑模版code", example = "capiture_getPayments")
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
    @ApiModelProperty(value = "数据转为json后的条件脚本", example = "def rule() { def amount = context.requestBody.amount\nif (amount <= 100) {return true;}\nreturn false} ")
    private String reqMatchRule;
    /**
     * 回参code
     */
    @ApiModelProperty(value = "返回的http code", example = "200、201、202、403、404、500、timeout.30")
    private String statusCode;
    /**
     * 回参模版，根据模版生成对应回参
     */
    @ApiModelProperty(value = "返回的模板", example = "{\"error_type\":\"processing_error\",\"requestBody\":$requestBody,\"error_codes\":[\"token_invalid\"],\"request_id\":\"9307c0c4-8e75-4d44-b900-84de486f3ef5\"}\n")
    private String responseTemplate;
}
