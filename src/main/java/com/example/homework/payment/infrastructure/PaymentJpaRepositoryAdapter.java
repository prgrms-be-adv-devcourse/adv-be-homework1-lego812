package com.example.homework.payment.infrastructure;

import com.example.homework.payment.domain.Payment;
import com.example.homework.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentJpaRepositoryAdapter implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return paymentJpaRepository.findAll(pageable);
    }

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(payment);
    }

}
