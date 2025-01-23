package org.pogonin.authservice.api.controller;

import lombok.RequiredArgsConstructor;
import org.pogonin.authservice.api.dto.in.ConfirmationRequest;
import org.pogonin.authservice.api.dto.in.LoginRequest;
import org.pogonin.authservice.api.dto.in.RegistrationRequest;
import org.pogonin.authservice.core.service.impl.AuthenticationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authService;

    @PostMapping("register")
    public void register(@RequestBody RegistrationRequest request) {
        authService.register(request);
    }

    @PostMapping("login")
    public String login(@RequestBody LoginRequest request) {
        return authService.authenticated(request.getEmail(), request.getPassword());
    }

    @PostMapping("confirm")
    public String confirm(@RequestBody ConfirmationRequest request) {
        return authService.confirmEmail(request.getEmail(), request.getCode());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("hello")
    public String hello() {
        return "hi hi";
    }
}
