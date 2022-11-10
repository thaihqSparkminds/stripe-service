package com.example.stripe.controller.publics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.ProductService;
import com.example.stripe.service.dto.ProductInfoDto;
import com.example.stripe.service.dto.request.PriceRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private ProductService service;

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody ProductInfoDto dto) {
        return ResponseEntity.ok(service.createProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable String id, @RequestBody ProductInfoDto dto) {
        return ResponseEntity.ok(service.updateProduct(id, dto));
    }

    @PostMapping("/price")
    public ResponseEntity<Object> createPrice(@RequestBody PriceRequestDto dto) {
        return ResponseEntity.ok(service.createPrice(dto));
    }

    @PutMapping("/price/{id}")
    public ResponseEntity<Object> updatePrice(@PathVariable String id, @RequestBody PriceRequestDto dto) {
        return ResponseEntity.ok(service.updatePrice(id, dto));
    }
}
