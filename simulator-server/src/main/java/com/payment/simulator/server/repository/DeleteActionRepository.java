package com.payment.simulator.server.repository;

import com.payment.simulator.server.entity.DeleteAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeleteActionRepository extends JpaRepository<DeleteAction, Integer> {
    Optional<DeleteAction> findByDeleteKey(String deleteKey);
}