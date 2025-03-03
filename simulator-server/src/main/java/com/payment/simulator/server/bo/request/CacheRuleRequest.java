package com.payment.simulator.server.bo.request;

import com.payment.simulator.server.bo.base.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Danny
 * @version v1.0
 * @date 2022/4/7
 **/
@Data
public class CacheRuleRequest extends Page {

    /**
     * id
     */
    @ApiModelProperty(value = "id", example = "主键")
    private String id;

    /**
     * mock规则表id
     */
    @ApiModelProperty(value = "关联mockRuleId", example = "主键")
    private String mockRuleId;
    /**
     * 缓存操作
     */
    @ApiModelProperty(value = "缓存操作", example = "put、get")
    private String cacheOption;

    /**
     * 获取入参中key规则
     */
    @ApiModelProperty(value = "获取入参中key规则", example = "[\"id\",\"type\"]")
    private String reqCacheRule;
    /**
     * 缓存数据
     */
    @ApiModelProperty(value = "缓存数据模板")
    private String cacheBody;

    /**
     * 跟缓存中数据匹配规则
     */
    @ApiModelProperty(value = "跟缓存中数据匹配规则")
    private String cacheBodyMatchRule;
    /**
     * 缓存时间，单位秒
     */
    @ApiModelProperty(value = "缓存时间，单位秒", example = "3600")
    private Long cacheTime;

    /**
     * 返回值模版
     */
    @ApiModelProperty(value = "跟缓存中数据匹配规则")
    private String responseTemplate;

    /**
     * 缓存过期后返回值模版
     */
    @ApiModelProperty(value = "跟缓存中数据匹配规则")
    private String nullReponseTemplate;

    /**
     * 匹配cacheBodyMatchRule失败返回的模板
     */
    @ApiModelProperty(value = "匹配cacheBodyMatchRule失败返回的模板")
    private String matchErrorResponseTemplate;
    /**
     * 匹配成功返回的code
     */
    @ApiModelProperty(value = "匹配成功返回的code", example = "200、201、202、403、404、500")
    private String matchStatusCode;
    /**
     * 缓存过期后返回值Code
     */
    @ApiModelProperty(value = "缓存过期后返回值Code", example = "200、201、202、403、404、500")
    private String nullMatchStatusCode;
    /**
     * 匹配cacheBodyMatchRule失败返回的code
     */
    @ApiModelProperty(value = "匹配cacheBodyMatchRule失败返回的code", example = "200、201、202、403、404、500")
    private String matchErrorStatusCode;
    @ApiModelProperty(value = "备注")
    private String remark;
}
