package com.common.api.simulator.server.repository;

import com.common.api.simulator.server.entity.QueryCacheAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryCacheActionRepository extends JpaRepository<QueryCacheAction, Integer> {
}