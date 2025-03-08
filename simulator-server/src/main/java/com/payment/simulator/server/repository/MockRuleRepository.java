package com.payment.simulator.server.repository;

import com.payment.simulator.server.entity.MockRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MockRuleRepository extends JpaRepository<MockRule, String> {
    List<MockRule> findByChannelId(String channelId);
    List<MockRule> findByPath(String path);
    List<MockRule> findByChannelIdAndPathAndRequestMethodAndContentType(String channelId, String path, String method, String contentType);
}