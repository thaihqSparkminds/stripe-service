package com.example.stripe.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.stripe.service.PaymentService;
import com.example.stripe.service.dto.CardDto;
import com.example.stripe.service.dto.CustomerDto;
import com.example.stripe.service.dto.request.PaymentRequestDto;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Coupon;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import com.stripe.model.Token;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    @Override
    public String createCustomer(String email, String token) {
        String id = null;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> customerParams = new HashMap<>();
            // add customer unique id here to track them in your web application
            customerParams.put("description", "Customer for " + email);
            customerParams.put("email", email);

            customerParams.put("source", token); // ^ obtained with Stripe.js
            // create a new customer
            Customer customer = Customer.create(customerParams);
            id = customer.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    @Override
    public String createSubscription(String customerId, String plan, String coupon) {
        String id = null;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> item = new HashMap<>();
            item.put("plan", plan);

            Map<String, Object> items = new HashMap<>();
            items.put("0", item);

            Map<String, Object> params = new HashMap<>();
            params.put("customer", customerId);
            params.put("items", items);

            // add coupon if available
            if (!coupon.isEmpty()) {
                params.put("coupon", coupon);
            }

            Subscription sub = Subscription.create(params);
            id = sub.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    @Override
    public boolean cancelSubscription(String subscriptionId) {
        boolean status;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Subscription sub = Subscription.retrieve(subscriptionId);
            sub.cancel(null);
            status = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            status = false;
        }
        return status;
    }

    @Override
    public Coupon retrieveCoupon(String code) {
        try {
            Stripe.apiKey = API_SECRET_KEY;
            return Coupon.retrieve(code);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public String charge(PaymentRequestDto chargeRequest) {
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", chargeRequest.getAmount());
            chargeParams.put("currency", PaymentRequestDto.Currency.INR);
            chargeParams.put("source", chargeRequest.getToken().getId());

            Charge charge = Charge.create(chargeParams);
            return charge.getId();
        } catch (Exception e) {
            throw new com.example.stripe.exception.StripeException("charge fail");
        }
    }

    @Override
    public void createCustomer(CustomerDto dto) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("email", dto.getEmail());
            Customer customer = Customer.create(map);
        } catch (Exception e) {
        }
    }

    @Override
    public void addCard(CardDto cardDto) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("number", cardDto.getNumber());
            map.put("exp_month", cardDto.getExpMonth());
            map.put("exp_year", cardDto.getExpYear());
            map.put("cvc", cardDto.getCvc());
            Map<String, Object> cardMap = new HashMap<>();
            cardMap.put("card", map);
            Token token = Token.create(cardMap);
            Customer cust = Customer.retrieve(cardDto.getUserId());
            Map<String, Object> source = new HashMap<>();
            source.put("source", token.getId());
            cust.getSources().create(source);
        } catch (Exception e) {
        }
    }
}
