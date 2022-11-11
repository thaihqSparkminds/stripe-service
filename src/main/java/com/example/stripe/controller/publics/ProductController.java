package com.example.stripe.controller.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    ProductService service;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductInfoDto dto) {
        return ResponseEntity.ok(service.createProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable String id, @RequestBody ProductInfoDto dto) {
        service.updateProduct(id, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/enable/{id}")
    public ResponseEntity<Void> enableProduct(@PathVariable String id) {
        service.enableProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disable/{id}")
    public ResponseEntity<Void> disableProduct(@PathVariable String id) {
        service.disableProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/price")
    public ResponseEntity<Object> createPrice(@RequestBody PriceRequestDto dto) {
        return ResponseEntity.ok(service.createPrice(dto));
    }

    @PutMapping("/price/{id}")
    public ResponseEntity<Object> updatePrice(@PathVariable String id, @RequestBody PriceRequestDto dto) {
        service.updatePrice(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/price/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable String id) {
        service.deletePrice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/price/enable/{id}")
    public ResponseEntity<Void> enablePrice(@PathVariable String id) {
        service.enablePrice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/price/disable/{id}")
    public ResponseEntity<Void> disablePrice(@PathVariable String id) {
        service.disablePrice(id);
        return ResponseEntity.noContent().build();
    }
}
