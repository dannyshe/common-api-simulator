package com.payment.simulator.server.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "mock_rule")
public class MockRule {

    @Id
    private String id;

    @Column(name = "channel_id")
    private String channelId;

    @Column(name = "path")
    private String path;

    @Column(name = "request_method", length = 20)
    private String requestMethod;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "template_code")
    private String templateCode;

    @Column(name = "req_reg_rule")
    private String reqRegRule;

    @Column(name = "req_json_path")
    private String reqJsonPath;

    @Column(name = "req_match_rule")
    private String reqMatchRule;

    @Column(name = "status_code", length = 20)
    private String statusCode;

    @Column(name = "response_template", columnDefinition = "text")
    private String responseTemplate;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @Column(name = "remark", columnDefinition = "text")
    private String remark;
}
