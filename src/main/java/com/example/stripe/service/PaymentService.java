package com.example.stripe.service;
import com.example.stripe.service.dto.CardDto;
import com.example.stripe.service.dto.CustomerDto;
import com.example.stripe.service.dto.request.PaymentRequestDto;
import com.stripe.model.Coupon;

public interface PaymentService {

    String createCustomer(String email, String token);

    String createSubscription(String customerId, String plan, String coupon);

    boolean cancelSubscription(String subscriptionId);

    Coupon retrieveCoupon(String code);

    String charge(PaymentRequestDto chargeRequest);

    void createCustomer(CustomerDto dto);

    void addCard(CardDto dto);
}