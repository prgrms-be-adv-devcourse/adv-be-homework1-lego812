package com.example.homework.payment.application.dto;

public record PaymentCommand (
        Long amount,
        String orderId,
        String paymentKey
){ }
