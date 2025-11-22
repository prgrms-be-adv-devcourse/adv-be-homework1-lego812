package com.example.homework.service;

import com.example.homework.entity.PurchaseOrderStatus;
import com.example.homework.entity.ResponseEntity;
import com.example.homework.entity.PurchaseOrder;
import com.example.homework.repository.OrderJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class OrderService {

    @Autowired
    private OrderJpaRepository orderRepository;

    public ResponseEntity<PurchaseOrder> create(PurchaseOrder order) {
        return new ResponseEntity<>(HttpStatus.CREATED.value(),orderRepository.save(order),1 );
    }

    public ResponseEntity<List<PurchaseOrder>> findAll(Pageable pageable) {
        Page<PurchaseOrder> ordersPage = orderRepository.findAll(pageable);
        List<PurchaseOrder> order = ordersPage.getContent();
        return new ResponseEntity<>(HttpStatus.OK.value(), order, ordersPage.getTotalElements());
    }

    public ResponseEntity<PurchaseOrder> paid(String id) {
        return statusChanged(id, PurchaseOrderStatus.PAID);
    }

    public ResponseEntity<PurchaseOrder> cancel(String id) {
        return statusChanged(id,PurchaseOrderStatus.CANCELLED);
    }


    //dirtyChecking 이용한 status update
    //findById로 order 값 불러오기.
    //만약 값 존재하면 status 바꿈
    //존재하지 않으면 예외처리
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
