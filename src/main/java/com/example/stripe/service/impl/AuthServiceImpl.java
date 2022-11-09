package com.example.stripe.service.impl;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.stripe.config.StripeProperties;
import com.example.stripe.entity.User;
import com.example.stripe.entity.UserSession;
import com.example.stripe.exception.BadRequestException;
import com.example.stripe.exception.UnauthorizedException;
import com.example.stripe.repository.UserRepository;
import com.example.stripe.repository.UserSessionRepository;
import com.example.stripe.security.JwtProvider;
import com.example.stripe.security.dto.JwtTokenDto;
import com.example.stripe.service.AuthService;
import com.example.stripe.service.RedisService;
import com.example.stripe.service.dto.ResetPasswordDto;
import com.example.stripe.service.dto.UserDto;
import com.example.stripe.service.dto.request.LoginDto;
import com.example.stripe.service.dto.request.SignupDto;
import com.example.stripe.service.dto.response.LoginResultDto;
import com.example.stripe.service.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    /*** Repositories */
    private final UserSessionRepository sessionRepository;
    private final UserRepository userRepository;

    /** Services */
    private final RedisService redisService;

    /** Others */
    private final StripeProperties properties;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;

    @Override
    public void logout(String sessionId) {
        Optional<UserSession> userSession = sessionRepository.findBySessionId(sessionId);
        if (userSession.isEmpty())
            return;

        sessionRepository.delete(userSession.get());
        pushSessionIdToBlacklist(sessionId);
    }

    @Override
    public LoginResultDto login(@Valid LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(UnauthorizedException::new);

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException();
        }

        JwtTokenDto tokenDto = jwtProvider.createToken(user);
        UserSession session = saveUserSession(user, tokenDto);
        return LoginResultDto.builder()
                .token(tokenDto.getToken())
                .sessionId(session.getSessionId()).build();
    }

    @Override
    @Transactional
    public UserDto signup(@Valid SignupDto signupDto) {
        boolean isExistingEmail = userRepository.findByEmail(signupDto.getEmail()).isPresent();
        if (isExistingEmail) {
            throw new BadRequestException("Email already exist.");
        }
        User user = new User();
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        return userMapper.toDto(userRepository.save(user));
    }

    private UserSession saveUserSession(User user, JwtTokenDto tokenDto) {
        UserSession session = new UserSession(user, tokenDto);
        return sessionRepository.save(session);
    }

    private void pushSessionIdToBlacklist(String sessionId) {
        redisService.setData(String.format("blacklist:session:%s", sessionId), Boolean.TRUE,
                properties.getJwt().getTokenValidityInSeconds());
    }


    @Override
    @Transactional
    public Boolean resetPassword(@Valid ResetPasswordDto dto) {
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if(user.isEmpty()) return false;
        user.get().setPassword(passwordEncoder.encode(dto.getPassword()));
        return true;
    }
}
