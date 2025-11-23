package com.example.homework.order.application;

import com.example.homework.order.application.dto.OrderCommand;
import com.example.homework.order.application.dto.OrderInfo;
import com.example.homework.order.domain.OrderRepository;
import com.example.homework.order.domain.PurchaseOrder;
import com.example.homework.order.domain.PurchaseOrderStatus;
import com.example.homework.order.domain.ResponseEntity;
import com.example.homework.order.presentation.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional //dirty checking 작동되도록
public class OrderService {

    private final OrderRepository orderRepository;

    public ResponseEntity<OrderInfo> create(OrderCommand command) {
        PurchaseOrder order=PurchaseOrder.create(
                command.productId(),
                command.memberId(),
                command.amount(),
                command.sellerId()
        );
        PurchaseOrder purchased=orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.CREATED.value(), OrderInfo.from(purchased),1 );
    }

    public ResponseEntity<List<OrderInfo>> findAll(Pageable pageable) {
        Page<PurchaseOrder> ordersPage = orderRepository.findAll(pageable);
        List<OrderInfo> order = ordersPage.stream().
                map(OrderInfo::from)
                .toList();
        return new ResponseEntity<>(HttpStatus.OK.value(), order, ordersPage.getTotalElements());
    }

    public ResponseEntity<OrderInfo> paid(String id) {
        return statusChanged(id, PurchaseOrderStatus.PAID);
    }

    public ResponseEntity<OrderInfo> cancel(String id) {
        return statusChanged(id, PurchaseOrderStatus.CANCELLED);
    }

    //dirtyChecking 이용한 status update
    //findById로 order 값 불러오기.
    //만약 값 존재하면 status 바꿈
    //존재하지 않으면 예외처리
    public ResponseEntity<OrderInfo> statusChanged(String id, PurchaseOrderStatus status) {
        Optional<PurchaseOrder> order =orderRepository.findById(UUID.fromString(id));
        if (order.isPresent()) {
            PurchaseOrder item=order.get();
            item.changeStatus(status);
            return new ResponseEntity<>(HttpStatus.ACCEPTED.value(), OrderInfo.from(item),1);
        }else{
            throw new IllegalArgumentException("order not found id: "+id);
        }
    }
}
