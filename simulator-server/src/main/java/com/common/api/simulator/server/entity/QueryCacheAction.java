package com.common.api.simulator.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "query_cache_action")
public class QueryCacheAction {
    @Id
    private Integer id;
    
    @Column(name = "query_key_script")
    private String queryKeyScript;
    
    @Column(name = "response_code")
    private String responseCode;
    
    @Column(name = "not_found_response", columnDefinition = "text")
    private String notfoundResponse;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "description", columnDefinition = "text")
    private String desc;
}