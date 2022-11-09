package com.example.stripe.controller.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.PaymentService;
import com.example.stripe.service.dto.CardDto;
import com.example.stripe.service.dto.CustomerDto;
import com.example.stripe.service.dto.request.PaymentRequestDto;
import com.stripe.exception.StripeException;
@RestController
@RequestMapping("/payment")

public class PaymentController {

    @Autowired
    PaymentService service;

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDto dto){
        service.createCustomer(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> addCard(@RequestBody CardDto dto){
        service.addCard(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<String> completePayment(@RequestBody PaymentRequestDto request) throws StripeException {
        String chargeId= service.charge(request);
        return chargeId!=null? new ResponseEntity<String>(chargeId,HttpStatus.OK):
            new ResponseEntity<String>("Please check the credit card details entered",HttpStatus.BAD_REQUEST);
    }
}