package com.common.api.simulator.server.repository;

import com.common.api.simulator.server.entity.UpdateAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateActionRepository extends JpaRepository<UpdateAction, Integer> {
}