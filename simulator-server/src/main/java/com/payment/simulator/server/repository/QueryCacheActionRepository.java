package com.payment.simulator.server.repository;

import com.payment.simulator.server.entity.QueryCacheAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QueryCacheActionRepository extends JpaRepository<QueryCacheAction, Integer> {
}