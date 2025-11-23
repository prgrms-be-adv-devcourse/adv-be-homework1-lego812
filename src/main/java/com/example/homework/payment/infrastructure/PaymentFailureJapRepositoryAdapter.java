package com.example.homework.payment.infrastructure;

import com.example.homework.payment.domain.PaymentFailure;
import com.example.homework.payment.domain.PaymentFailureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentFailureJapRepositoryAdapter implements PaymentFailureRepository {
    private final PaymentFailureJpaRepository paymentFailureJpaRepository;

    @Override
    public Page<PaymentFailure> findAll(Pageable pageable){
        return paymentFailureJpaRepository.findAll(pageable);
    }

    @Override
    public PaymentFailure save(PaymentFailure pageFailure){
        return paymentFailureJpaRepository.save(pageFailure);
    }
}
