package com.example.stripe.service;
import com.example.stripe.service.dto.request.PaySessionRequestDto;
import com.example.stripe.service.dto.response.PaySessionResponseDto;

public interface PaymentService {
    PaySessionResponseDto createSession(PaySessionRequestDto dto);
}