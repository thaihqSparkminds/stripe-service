package com.example.stripe.service.dto.request;

import com.example.stripe.service.dto.ProductDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaySessionRequestDto {
    private String successUrl;
    private String cancelUrl;
    private ProductDto[] lineItems;
}
