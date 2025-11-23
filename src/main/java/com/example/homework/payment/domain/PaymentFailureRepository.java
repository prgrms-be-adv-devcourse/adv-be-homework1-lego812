package com.example.homework.payment.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentFailureRepository{
    Page<PaymentFailure> findAll(Pageable pageable);

    PaymentFailure save(PaymentFailure pageFailure);
}
