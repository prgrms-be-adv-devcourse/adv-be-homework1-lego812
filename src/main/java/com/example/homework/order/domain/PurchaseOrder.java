package com.example.homework.order.domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "\"purchase_order\"", schema = "public")
@Data //원래는 setter를 숨기지만 test의 편리함을 위해 setter허용
public class PurchaseOrder {

    @Id
    private UUID id;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "seller_id", nullable = false)
    private UUID sellerId;

    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PurchaseOrderStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public void markPaid() {
        this.status = PurchaseOrderStatus.PAID;
    }

    public void markCancelled() {
        this.status = PurchaseOrderStatus.CANCELLED;
    }

    protected PurchaseOrder() {}

    private PurchaseOrder(UUID productId, UUID memberId, BigDecimal amount, UUID sellerId) {
        this.id = UUID.randomUUID();
        this.productId = productId;
        this.memberId = memberId;
        this.sellerId = sellerId;
        this.status = PurchaseOrderStatus.CREATED;
        this.amount = BigDecimal.ZERO;
    }



    public static PurchaseOrder create(UUID productId, UUID memberId, BigDecimal amount, UUID sellerId){
        return new PurchaseOrder(productId, memberId, amount, sellerId);
    }

    public void changeStatus(PurchaseOrderStatus newStatus){
        this.status=newStatus;
    }
    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (id == null) {
            id = UUID.randomUUID();
        }
        createdAt = now;
        updatedAt = now;
        if (status == null) {
            status = PurchaseOrderStatus.CREATED;
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
