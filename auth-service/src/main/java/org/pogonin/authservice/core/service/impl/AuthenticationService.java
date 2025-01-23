package org.pogonin.authservice.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.pogonin.authservice.api.dto.in.RegistrationRequest;
import org.pogonin.authservice.core.exception.ConfirmCodeException;
import org.pogonin.authservice.core.security.JwtTokenService;
import org.pogonin.authservice.core.service.CodeGenerator;
import org.pogonin.authservice.db.entity.ConfirmationCode;
import org.pogonin.authservice.db.entity.Role;
import org.pogonin.authservice.db.entity.User;
import org.pogonin.authservice.db.repository.ConfirmCodeRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final ConfirmCodeRepository confirmCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final CodeGenerator codeGenerator;
    private final UserService userService;

    public String authenticated(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        return jwtTokenService.generateToken((User) userService.loadUserByUsername(email));
    }

    @Transactional
    public String confirmEmail(String email, String code) {
        ConfirmationCode dbCode = confirmCodeRepository.findByUserEmailAndCode(email, code)
                .orElseThrow(() -> new ConfirmCodeException("Confirm code not found or expired"));

        User user = dbCode.getUser();
        user.setConfirmed(true);

        return jwtTokenService.generateToken(user);
    }

    @Transactional
    public void register(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setConfirmed(false);
        user.setRoles(Set.of(Role.USER));
        user.setName(registrationRequest.getName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        userService.save(user);
        confirmCodeRepository.save(new ConfirmationCode(user, codeGenerator.generate()));
    }
}
