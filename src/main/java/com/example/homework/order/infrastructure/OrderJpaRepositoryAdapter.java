package com.example.homework.order.infrastructure;

import com.example.homework.order.application.OrderService;
import com.example.homework.order.domain.OrderRepository;
import com.example.homework.order.domain.PurchaseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderJpaRepositoryAdapter implements OrderRepository{

    private final OrderJpaRepository jpaRepository;


    @Override
    public PurchaseOrder save(PurchaseOrder order) {
        return jpaRepository.save(order);
    }

    @Override
    public Page<PurchaseOrder> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }

    @Override
    public Optional<PurchaseOrder> findById(UUID id) {
        return jpaRepository.findById(id);
    }
}
