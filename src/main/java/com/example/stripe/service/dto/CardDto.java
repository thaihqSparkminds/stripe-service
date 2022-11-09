package com.example.stripe.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    String number;
    String expMonth;
    String expYear;
    String cvc;
    String userId;
}
