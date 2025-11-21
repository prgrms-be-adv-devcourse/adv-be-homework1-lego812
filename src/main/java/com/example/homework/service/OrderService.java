package com.example.homework.service;

import com.example.homework.entity.PurchaseOrderStatus;
import com.example.homework.entity.ResponseEntity;
import com.example.homework.entity.PurchaseOrder;
import com.example.homework.repository.OrderJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderJpaRepository orderRepository;

    public ResponseEntity<PurchaseOrder> create(PurchaseOrder order) {
        return new ResponseEntity<>(HttpStatus.CREATED.value(),orderRepository.save(order),1 );
    }

    public ResponseEntity<List<PurchaseOrder>> findAll(Pageable pageable) {

    }

    public ResponseEntity<PurchaseOrder> statusChanged(String id, PurchaseOrderStatus status) {
        Optional<PurchaseOrder> order =orderRepository.findById(UUID.fromString(id));
        if (order.isPresent()) {
            PurchaseOrder item=order.get();
            item.setStatus(status);
            return new ResponseEntity<>(HttpStatus.ACCEPTED.value(), orderRepository.save(item),1);
        }else{
            throw new IllegalArgumentException("order not found id: "+id);
        }
    }

}
