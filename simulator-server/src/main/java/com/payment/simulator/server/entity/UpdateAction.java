package com.payment.simulator.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "update_action")
public class UpdateAction {
    @Id
    private Integer id;
    
    @Column(name = "update_key_script", columnDefinition = "text")
    private String updateKeyScript;
    
    @Column(name = "update_rule", columnDefinition = "text")
    private String updateRule;
    
    @Column(name = "update_condition", columnDefinition = "text")
    private String updateCondition;
    
    @Column(name = "response_code")
    private String responseCode;
    
    @Column(name = "response", columnDefinition = "text")
    private String response;

    @Column(name = "not_found_response", columnDefinition = "text")
    private String notfoundResponse;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "description", columnDefinition = "text")
    private String desc;
}