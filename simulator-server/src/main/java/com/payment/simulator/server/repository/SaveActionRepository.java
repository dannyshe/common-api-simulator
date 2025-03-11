package com.payment.simulator.server.repository;

import com.payment.simulator.server.entity.SaveAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveActionRepository extends JpaRepository<SaveAction, Integer> {
}