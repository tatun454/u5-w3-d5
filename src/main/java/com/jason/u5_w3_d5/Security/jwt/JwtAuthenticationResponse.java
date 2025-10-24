package com.jason.u5_w3_d5.Security.jwt;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtAuthenticationResponse {
    private final String accessToken;
    private final String tokenType = "Bearer";
}