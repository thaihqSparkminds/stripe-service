package com.example.stripe.controller.publics;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-session")
    public ResponseEntity<PaySessionResponseDto> createSession(@RequestBody PaySessionRequestDto dto) {
        return ResponseEntity.ok(paymentService.createSession(dto));
//        String url = paymentService.createSession(dto).getUrl();
//        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }
}