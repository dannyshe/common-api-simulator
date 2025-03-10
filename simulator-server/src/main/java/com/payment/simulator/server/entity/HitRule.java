package com.payment.simulator.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "hit_rule")
public class HitRule {
    @Id
    private Integer id;
    
    @Column(name = "channel_id")
    private String channelId;
    
    private String path;
    
    @Column(name = "request_method")
    private String requestMethod;
    
    @Column(name = "content_type")
    private String contentType;
    
    @Column(name = "path_variable_rule", columnDefinition = "text")
    private String pathVariableRule;
    
    @Column(name = "action_type")
    private String actionType;
    
    @Column(name = "action_id")
    private String actionId;
    
    @Column(name = "generate_id_script", columnDefinition = "text")
    private String generateIdScript;
    
    @Column(name = "cache_ttl")
    private Integer cacheTTLHours;
    
    @Column(name = "response_code")
    private String responseCode;
    
    @Column(name = "response", columnDefinition = "text")
    private String response;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "description", columnDefinition = "text")
    private String desc;
}