package com.example.homework.payment.application.dto;

import com.example.homework.payment.domain.Payment;
import com.example.homework.payment.domain.PaymentStatus;
import lombok.Builder;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record PaymentInfo(
        UUID id,
        String paymentKey,
        String orderId,
        Long amount,
        String method,
        PaymentStatus status,
        LocalDateTime requestedAt,
        LocalDateTime approvedAt,
        String failReason,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PaymentInfo from(Payment payment) {
        return PaymentInfo.builder()
                .id(UUID.randomUUID())
                .paymentKey(payment.getPaymentKey())
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .requestedAt(payment.getRequestedAt())
                .approvedAt(payment.getApprovedAt())
                .failReason(payment.getFailReason())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
