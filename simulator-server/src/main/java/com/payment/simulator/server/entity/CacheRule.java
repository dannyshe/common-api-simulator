package com.payment.simulator.server.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "cache_rule")
public class CacheRule {

    @Id
    private String id;

    @Column(name = "mock_rule_id")
    private String mockRuleId;

    @Column(name = "cache_option")
    private String cacheOption;

    @Column(name = "req_cache_rule")
    private String reqCacheRule;

    @Column(name = "cache_body", columnDefinition = "text")
    private String cacheBody;

    @Column(name = "cache_body_match_rule")
    private String cacheBodyMatchRule;

    @Column(name = "cache_time")
    private Long cacheTime;

    @Column(name = "match_error_response_template", columnDefinition = "text")
    private String matchErrorResponseTemplate;

    @Column(name = "null_response_template", columnDefinition = "text")
    private String nullResponseTemplate;

    @Column(name = "response_template", columnDefinition = "text")
    private String responseTemplate;

    @Column(name = "match_status_code")
    private String matchStatusCode;

    @Column(name = "null_match_status_code")
    private String nullMatchStatusCode;

    @Column(name = "match_error_status_code")
    private String matchErrorStatusCode;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @Column(name = "remark", columnDefinition = "text")
    private String remark;
}
