package com.payment.simulator.server.repository;

import com.payment.simulator.server.entity.HitRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HitRuleRepository extends JpaRepository<HitRule, Integer> {
    List<HitRule> findByChannelId(String channelId);
    List<HitRule> findByPath(String path);
    List<HitRule> findByChannelIdAndRequestMethodAndContentType(String channelId, String requestMethod, String contentType);
}