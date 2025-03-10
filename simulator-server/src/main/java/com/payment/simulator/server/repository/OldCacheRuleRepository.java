package com.payment.simulator.server.repository;

import com.payment.simulator.server.entity.OldCacheRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OldCacheRuleRepository extends JpaRepository<OldCacheRule, String> {
    List<OldCacheRule> findByMockRuleId(String mockRuleId);

    List<OldCacheRule> queryByMockRuleId(String mockRuleId);
}