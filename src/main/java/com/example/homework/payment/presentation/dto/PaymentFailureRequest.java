package com.example.homework.payment.presentation.dto;
import com.example.homework.payment.application.dto.PaymentFailureCommand;

public record PaymentFailureRequest(
        String orderId,
        String paymentKey,
        String errorCode,
        String errorMessage,
        Long amount,
        String rawPayload
) {
    public PaymentFailureCommand toCommand() {
        return new PaymentFailureCommand(orderId, paymentKey, errorCode, errorMessage, amount, rawPayload);
    }
}
