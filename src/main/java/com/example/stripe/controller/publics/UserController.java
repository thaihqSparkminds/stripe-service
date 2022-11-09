package com.example.stripe.controller.publics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.UserService;
import com.example.stripe.service.dto.response.UserDetailsResponseDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "API to getUserDetails")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(value = "/user/user-details", produces = "application/vn.sparkminds.api-v1+json")
    public ResponseEntity<UserDetailsResponseDto> getUserDetails(@RequestHeader String sessionId) {
        return ResponseEntity.ok().body(userService.getUserDetails(sessionId));
    }
}
