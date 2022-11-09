package com.example.stripe.service.impl;

import org.springframework.stereotype.Service;

import com.example.stripe.entity.UserSession;
import com.example.stripe.exception.BadRequestException;
import com.example.stripe.repository.UserSessionRepository;
import com.example.stripe.service.UserService;
import com.example.stripe.service.dto.response.UserDetailsResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserSessionRepository userSessionRepository;

    @Override
    public UserDetailsResponseDto getUserDetails(String sessionId) {
        UserSession userSession = userSessionRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new BadRequestException("Not found"));
        return UserDetailsResponseDto.builder().sessionid(userSession.getSessionId())
                .userId(userSession.getUser().getId()).email(userSession.getUser().getEmail()).build();
    }
}
