package com.payment.simulator.server.repository;

import com.payment.simulator.server.entity.OldMockRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OldMockRuleRepository extends JpaRepository<OldMockRule, String> {
    List<OldMockRule> findByChannelId(String channelId);
    List<OldMockRule> findByPath(String path);
    List<OldMockRule> findByChannelIdAndPathAndRequestMethodAndContentType(String channelId, String path, String method, String contentType);
}