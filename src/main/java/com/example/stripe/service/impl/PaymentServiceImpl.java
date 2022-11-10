package com.example.stripe.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.stripe.enums.PaySessionMode;
import com.example.stripe.exception.StripeException;
import com.example.stripe.service.PaymentService;
import com.example.stripe.service.dto.ProductDto;
import com.example.stripe.service.dto.request.PaySessionRequestDto;
import com.example.stripe.service.dto.response.PaySessionResponseDto;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    @Override
    public PaySessionResponseDto createSession(PaySessionRequestDto dto) {

        Stripe.apiKey = API_SECRET_KEY;
        List<Object> lineItems = new ArrayList<>();

        for (ProductDto product : dto.getLineItems()) {
            Map<String, Object> lineItem = new HashMap<>();
            lineItem.put("price", product.getPrice());
            lineItem.put("quantity", product.getQuantity());
            
            lineItems.add(lineItem);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("success_url", dto.getSuccessUrl());
        params.put("cancel_url", dto.getCancelUrl());
        params.put("line_items", lineItems);
        params.put("mode", PaySessionMode.payment);
        try {
            Session session = Session.create(params);
            return PaySessionResponseDto.builder().url(session.getUrl()).build();
        } catch (Exception e) {
            throw new StripeException(e.getMessage());
        }
    }
}
