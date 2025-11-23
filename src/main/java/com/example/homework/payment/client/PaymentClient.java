package com.example.homework.payment.client;

import com.example.homework.payment.application.dto.PaymentCommand;
import com.example.homework.payment.client.dto.TossPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentClient {


        private static final String CONFIRM_URL = "https://api.tosspayments.com/v1/payments/confirm";
        private final RestTemplate restTemplate;

        @Value("${payment.toss.secret-key}")
        private String secretKey;


        //결제승인요청 메소드
        public TossPaymentResponse confirm(PaymentCommand command) {

            HttpHeaders headers = createHeaders();

            Map<String, Object> body = new HashMap<>();
            body.put("paymentKey", command.paymentKey());
            body.put("orderId", command.orderId());
            body.put("amount", command.amount());

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            try {
                return restTemplate.postForObject(CONFIRM_URL, entity, TossPaymentResponse.class);
            } catch (HttpStatusCodeException ex) {
                HttpStatusCode statusCode = ex.getStatusCode();
                String responseBody = ex.getResponseBodyAsString();
                throw new IllegalStateException("Toss confirm failed (" + statusCode + "): " + responseBody, ex);
            }
        }

        private HttpHeaders createHeaders() {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String auth = secretKey + ":";
            String encoded = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            headers.set(HttpHeaders.AUTHORIZATION, "Basic " + encoded);
            return headers;
        }
    }