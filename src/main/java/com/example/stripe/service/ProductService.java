package com.example.stripe.service;

import com.example.stripe.service.dto.ProductInfoDto;
import com.example.stripe.service.dto.request.PriceRequestDto;

public interface ProductService {
    Object createProduct(ProductInfoDto dto);

    Object updateProduct(String id, ProductInfoDto dto);

    Object createPrice(PriceRequestDto dto);

    Object updatePrice(String id, PriceRequestDto dto);
}
