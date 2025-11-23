package com.example.homework.payment.application;

import com.example.homework.Common.ResponseEntity;
import com.example.homework.order.application.OrderService;
import com.example.homework.order.domain.PurchaseOrder;
import com.example.homework.payment.application.dto.PaymentCommand;
import com.example.homework.payment.application.dto.PaymentFailureCommand;
import com.example.homework.payment.application.dto.PaymentFailureInfo;
import com.example.homework.payment.application.dto.PaymentInfo;
import com.example.homework.payment.client.PaymentClient;
import com.example.homework.payment.client.dto.TossPaymentResponse;
import com.example.homework.payment.domain.Payment;
import com.example.homework.payment.domain.PaymentFailure;
import com.example.homework.payment.domain.PaymentFailureRepository;
import com.example.homework.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentClient paymentClient;
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final PaymentFailureRepository paymentFailureRepository;

    public ResponseEntity<List<PaymentInfo>> findAll(Pageable pageable) {
        Page<Payment> all = paymentRepository.findAll(pageable);
        List<PaymentInfo> list = all.stream()
                .map(PaymentInfo::from).toList();
        return new ResponseEntity<>(HttpStatus.OK.value(), list, all.getTotalElements());
    }

    public ResponseEntity<PaymentInfo> confirm(PaymentCommand command) {
        TossPaymentResponse tossPayment = paymentClient.confirm(command);
        UUID orderId = UUID.fromString(tossPayment.orderId());
        PurchaseOrder order = orderService.findById(orderId);
        Payment payment = Payment.create(
                tossPayment.paymentKey(),
                tossPayment.orderId(),
                tossPayment.totalAmount()
        );
        LocalDateTime approvedAt = tossPayment.approvedAt() != null ? tossPayment.approvedAt().toLocalDateTime() : null;
        LocalDateTime requestedAt = tossPayment.requestedAt() != null ? tossPayment.requestedAt().toLocalDateTime() : null;
        payment.markConfirmed(tossPayment.method(), approvedAt, requestedAt);

        Payment saved = paymentRepository.save(payment);
        orderService.markPaid(order);
//        SellerSettlement settlement = SellerSettlement.create(
//                order.getSellerId(),
//                order.getId(),
//                order.getAmount()
//        );
//        sellerSettlementRepository.save(settlement);
        return new ResponseEntity<>(HttpStatus.CREATED.value(), PaymentInfo.from(saved), 1);
    }

    public ResponseEntity<PaymentFailureInfo> fail(PaymentFailureCommand command){
        PaymentFailure paymentFailure = PaymentFailure.from(command.orderId(),
                command.paymentKey(),
                command.errorCode(),
                command.errorMessage(),
                command.amount(),
                command.rawPayload()
        );
        paymentFailureRepository.save(paymentFailure);
        return new ResponseEntity<>(HttpStatus.OK.value(), PaymentFailureInfo.from(paymentFailure),1);
    }
}
