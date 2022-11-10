package com.example.stripe.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.stripe.exception.StripeException;
import com.example.stripe.service.ProductService;
import com.example.stripe.service.dto.ProductInfoDto;
import com.example.stripe.service.dto.request.PriceRequestDto;
import com.stripe.Stripe;
import com.stripe.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    @Override
    public Object createProduct(ProductInfoDto dto) {
        Stripe.apiKey = API_SECRET_KEY;

        Map<String, Object> params = new HashMap<>();
        params.put("name", dto.getName());
        params.put("description", dto.getDescription());
        params.put("active", dto.isActive());
        params.put("image", dto.getImage());
        try {
            Product product = Product.create(params);
            return product;
        } catch (Exception e) {
            throw new StripeException(e.getMessage());
        }
    }

    @Override
    public Object updateProduct(String id, ProductInfoDto dto) {
        Stripe.apiKey = API_SECRET_KEY;

        try {
            Product product = Product.retrieve(id);
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("order_id", "6735");
            Map<String, Object> params = new HashMap<>();
            params.put("metadata", metadata);

            Product updatedProduct = product.update(params);
            return updatedProduct;
        } catch (Exception e) {
            throw new StripeException(e.getMessage());
        }

    }

    @Override
    public Object createPrice(PriceRequestDto dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object updatePrice(String id, PriceRequestDto dto) {
        // TODO Auto-generated method stub
        return null;
    }

}
