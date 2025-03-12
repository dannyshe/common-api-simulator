package com.common.api.simulator.server.repository;

import com.common.api.simulator.server.entity.DeleteAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeleteActionRepository extends JpaRepository<DeleteAction, Integer> {
}