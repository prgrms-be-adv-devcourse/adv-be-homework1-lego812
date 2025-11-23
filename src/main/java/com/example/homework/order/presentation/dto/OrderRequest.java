package com.example.homework.order.presentation.dto;

import com.example.homework.order.application.dto.OrderCommand;

import java.math.BigDecimal;
import java.util.UUID;


public record OrderRequest (
        UUID productId,
        UUID sellerId,
        UUID memberId,
        BigDecimal amount
){

    public OrderCommand toCommand(){
        return new OrderCommand(productId, sellerId, memberId, amount);
    }
}
