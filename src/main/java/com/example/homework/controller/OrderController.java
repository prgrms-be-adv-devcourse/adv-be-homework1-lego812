package com.example.homework.controller;


import com.example.homework.entity.ResponseEntity;
import com.example.homework.entity.PurchaseOrder;
import com.example.homework.service.OrderService;
import com.example.homework.order.application.dto.OrderInfo;
import com.example.homework.order.presentation.dto.OrderRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.v1}/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "주문 생성", description = "상품과 구매자 정보를 바탕으로 주문을 생성한다.")
    @PostMapping
    public ResponseEntity<PurchaseOrder> create(@RequestBody PurchaseOrder request) {
        return orderService.create(request);
    }

    @Operation(summary = "주문 목록 조회", description = "생성된 주문을 페이지 단위로 조회한다.")
    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> findAll(Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @Operation(summary = "주문 상태 변경", description = "주문의 상태를 변경한다.")
    @GetMapping("{id}/paid")
    public ResponseEntity<PurchaseOrder>> findAll(@PathVariable String id) {
        return orderService.paid(id);
    }


    @Operation(summary = "주문 취소 ", description = "주문의 상태를 취소로 변경한다.")
    @GetMapping("{id}/paid")
    public ResponseEntity<Void> findAll(@PathVariable String id) {
        return orderService.cancel(id);
    }
}