package com.example.homework.order.infrastructure;

import com.example.homework.order.domain.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<PurchaseOrder, UUID> {
    Page<PurchaseOrder> findAll(Pageable pageable);
}
