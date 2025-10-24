package com.jason.u5_w3_d5.Controller;

import com.jason.u5_w3_d5.Security.jwt.JwtAuthenticationResponse;
import com.jason.u5_w3_d5.Security.jwt.JwtTokenProvider;
import com.jason.u5_w3_d5.Service.UserService;
import com.jason.u5_w3_d5.dto.LoginDTO;
import com.jason.u5_w3_d5.dto.UserRegistrationDTO;
import com.jason.u5_w3_d5.entity.Role;
import com.jason.u5_w3_d5.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/register/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerNormalUser(@RequestBody UserRegistrationDTO registrationDTO) {
        return userService.registerUser(registrationDTO.getUsername(), registrationDTO.getPassword(), Role.UTENTE_NORMALE);
    }

    @PostMapping("/register/organizer")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerOrganizer(@RequestBody UserRegistrationDTO registrationDTO) {
        return userService.registerUser(registrationDTO.getUsername(), registrationDTO.getPassword(), Role.ORGANIZZATORE_EVENTI);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);


        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
}