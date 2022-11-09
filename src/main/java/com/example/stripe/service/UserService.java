package com.example.stripe.service;

import com.example.stripe.service.dto.response.UserDetailsResponseDto;

public interface UserService {

    UserDetailsResponseDto getUserDetails(String sessionId);
}
