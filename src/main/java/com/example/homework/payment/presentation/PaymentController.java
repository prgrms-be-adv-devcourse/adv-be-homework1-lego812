package com.example.homework.payment.presentation;

import com.example.homework.Common.ResponseEntity;
import com.example.homework.order.application.dto.OrderInfo;
import com.example.homework.payment.application.PaymentService;
import com.example.homework.payment.application.dto.PaymentFailureInfo;
import com.example.homework.payment.application.dto.PaymentInfo;
import com.example.homework.payment.domain.PaymentFailure;
import com.example.homework.payment.infrastructure.PaymentJpaRepository;
import com.example.homework.payment.presentation.dto.PaymentFailureRequest;
import com.example.homework.payment.presentation.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.v1}/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentInfo>> findAll(@RequestBody Pageable pageable) {
        return paymentService.findAll(pageable);
    }

    @PostMapping("/confirm")
    public ResponseEntity<PaymentInfo> confirm(@RequestBody PaymentRequest request) {
        return paymentService.confirm(request.toCommand());
    }

    @PostMapping("/fail")
    public ResponseEntity<PaymentFailureInfo> fail(@RequestBody PaymentFailureRequest request) {
        return paymentService.fail(request.toCommand());
    }
}
