package com.example.stripe.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.stripe.exception.StripeException;
import com.example.stripe.service.ProductService;
import com.example.stripe.service.dto.ProductInfoDto;
import com.example.stripe.service.dto.request.PriceRequestDto;
import com.stripe.Stripe;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceUpdateParams;
import com.stripe.param.ProductUpdateParams;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    @Override
    public String createProduct(ProductInfoDto dto) {
        Stripe.apiKey = API_SECRET_KEY;

        Map<String, Object> defaultPriceData = new HashMap<>();
        if (dto.getDefaultPriceData() != null) {
            defaultPriceData.put("currency", dto.getDefaultPriceData().getCurrency());
            defaultPriceData.put("unit_amount", dto.getDefaultPriceData().getUnitAmount());
        }

        Map<String, Object> params = new HashMap<>();
        params.put("name", dto.getName());
        params.put("description", dto.getDescription());
        params.put("images", dto.getImages());
        params.put("default_price_data", defaultPriceData);
        params.put("active", dto.isActive());

        try {
//            if (dto.getDefaultPriceData() != null) {
//                params.put("active", dto.isActive());
//            }
            Product product = Product.create(params);

            return product.getId();
        } catch (Exception e) {
            throw new StripeException(e.getMessage());
        }
    }

    @Override
    public void updateProduct(String id, ProductInfoDto dto) {
        Stripe.apiKey = API_SECRET_KEY;

        try {
            Product product = Product.retrieve(id);
            ProductUpdateParams params = ProductUpdateParams.builder()
                    .setActive(dto.isActive())
                    .setName(dto.getName())
                    .setDescription(dto.getDescription())
                    .setImages(Arrays.asList(dto.getImages()))
                    .build();
            product.update(params);
        } catch (Exception e) {
            throw new StripeException(e.getMessage());
        }
    }

    @Override
    public Object createPrice(PriceRequestDto dto) {
        Stripe.apiKey = API_SECRET_KEY;

        Map<String, Object> params = new HashMap<>();
        params.put("unit_amount", dto.getUnitAmount());
        params.put("currency", dto.getCurrency());
        params.put("product", dto.getProduct());
        params.put("nickname", dto.getNickName());

        try {
            Price price = Price.create(params);
            return price.getId();
        } catch (Exception e) {
            throw new StripeException(e.getMessage());
        }
    }

    @Override
    public void updatePrice(String id, PriceRequestDto dto) {
        Stripe.apiKey = API_SECRET_KEY;
        try {
            Price resource = Price.retrieve(id);
            PriceUpdateParams params = PriceUpdateParams
                    .builder()
                    .setNickname(dto.getNickName())
                    .build();
            resource.update(params);
        } catch (Exception e) {
            throw new StripeException(e.getMessage());
        }
    }

    @Override
    public void deletePrice(String id) {
        try {
            Price product = Price.retrieve(id);
            PriceUpdateParams params = PriceUpdateParams.builder().setActive(false).build();
            product.update(params);
        } catch (Exception e) {
            throw new StripeException(e.getMessage());
        }
    }

    @Override
    public void enableProduct(String id) {
        try {
            Product product = Product.retrieve(id);
            ProductUpdateParams params = ProductUpdateParams.builder().setActive(true).build();
            product.update(params);
        } catch (Exception e) {
            throw new StripeException(e.getMessage());
        }
    }

    @Override
    public void disableProduct(String id) {
        try {
            Product product = Product.retrieve(id);
            ProductUpdateParams params = ProductUpdateParams.builder().setActive(false).build();
            product.update(params);
        } catch (Exception e) {
            throw new StripeException(e.getMessage());
        }
    }

    @Override
    public void enablePrice(String id) {
        try {
            Price price = Price.retrieve(id);
            PriceUpdateParams params = PriceUpdateParams.builder().setActive(true).build();
            price.update(params);
        } catch (Exception e) {
            throw new StripeException(e.getMessage());
        }
    }

    @Override
    public void disablePrice(String id) {
        try {
            Price price = Price.retrieve(id);
            PriceUpdateParams params = PriceUpdateParams.builder().setActive(false).build();
            price.update(params);
        } catch (Exception e) {
            throw new StripeException(e.getMessage());
        }
    }
}
