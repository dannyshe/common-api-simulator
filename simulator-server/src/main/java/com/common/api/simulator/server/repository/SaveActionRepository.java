package com.common.api.simulator.server.repository;

import com.common.api.simulator.server.entity.SaveAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveActionRepository extends JpaRepository<SaveAction, Integer> {
}