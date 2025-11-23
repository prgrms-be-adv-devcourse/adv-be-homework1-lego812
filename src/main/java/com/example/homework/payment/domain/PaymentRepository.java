package com.example.homework.payment.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentRepository {
    Page<Payment> findAll(Pageable pageable);

    Payment save(Payment payment);
}
