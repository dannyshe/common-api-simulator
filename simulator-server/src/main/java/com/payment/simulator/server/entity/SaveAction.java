package com.payment.simulator.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "save_action")
public class SaveAction {
    @Id
    private Integer id;
    
    @Column(name = "generate_key_script", columnDefinition = "text")
    private String generateKeyScript;
    
    @Column(name = "cache_ttl")
    private Integer cacheTtlHours;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "description", columnDefinition = "text")
    private String description;
}