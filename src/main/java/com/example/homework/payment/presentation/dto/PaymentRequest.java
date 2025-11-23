package com.example.homework.payment.presentation.dto;

import com.example.homework.payment.application.dto.PaymentCommand;

public record PaymentRequest(
        Long amount,
        String orderId,
        String paymentKey
) {
    public PaymentCommand toCommand() {
        return new PaymentCommand(amount, orderId, paymentKey);
    }
}
