package com.example.homework.repository;

import com.example.homework.entity.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<PurchaseOrder, UUID> {
    Page<PurchaseOrder> findAll(Pageable pageable);
}
