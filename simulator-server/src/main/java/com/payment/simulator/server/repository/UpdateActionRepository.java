package com.payment.simulator.server.repository;

import com.payment.simulator.server.entity.UpdateAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UpdateActionRepository extends JpaRepository<UpdateAction, Integer> {
    Optional<UpdateAction> findByUpdateKey(String updateKey);
}