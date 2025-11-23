package com.example.homework.order.application.dto;

import com.example.homework.order.domain.PurchaseOrder;
import com.example.homework.order.domain.PurchaseOrderStatus;
import lombok.Builder;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record OrderInfo(

        UUID id,
        UUID productId,
        UUID sellerId,
        UUID memberId,
        BigDecimal amount,
        PurchaseOrderStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static OrderInfo from(PurchaseOrder order){
        return OrderInfo.builder().
                id(order.getId()).
                productId(order.getProductId()).
                sellerId(order.getSellerId()).
                memberId(order.getMemberId()).
                amount(order.getAmount()).
                status(order.getStatus()).
                createdAt(order.getCreatedAt()).
                updatedAt(order.getUpdatedAt()).
                build();
    }
}
