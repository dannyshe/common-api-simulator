package com.payment.simulator.server.repository;

import com.payment.simulator.server.entity.CacheRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CacheRuleRepository extends JpaRepository<CacheRule, String> {
    List<CacheRule> findByMockRuleId(String mockRuleId);

    List<CacheRule> queryByMockRuleId(String mockRuleId);
}