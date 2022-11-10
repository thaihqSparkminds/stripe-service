package com.example.stripe.controller.publics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.PaymentService;
import com.example.stripe.service.dto.request.PaySessionRequestDto;
import com.example.stripe.service.dto.response.PaySessionResponseDto;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private PaymentService service;

    @PostMapping("/create-session")
    public ResponseEntity<PaySessionResponseDto> createSession(@RequestBody PaySessionRequestDto dto) {
        return ResponseEntity.ok(service.createSession(dto));
    }
}