package org.pogonin.authservice.core;

import lombok.RequiredArgsConstructor;
import org.pogonin.authservice.api.dto.in.RegistrationRequest;
import org.pogonin.authservice.core.exception.ConfirmCodeException;
import org.pogonin.authservice.core.exception.UserNotFoundException;
import org.pogonin.authservice.db.entity.ConfirmationCode;
import org.pogonin.authservice.db.entity.User;
import org.pogonin.authservice.port.out.db.ConfirmCodeRepository;
import org.pogonin.authservice.port.out.db.UserRepository;
import org.pogonin.authservice.mapper.UserMapper;
import org.pogonin.authservice.security.JwtTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final ConfirmCodeRepository confirmCodeRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CodeGenerator codeGenerator;
    private final JwtTokenService jwtTokenService;

    @Transactional
    public void register(RegistrationRequest registrationRequest) {
        User user = userMapper.toUser(registrationRequest);
        userRepository.save(user);
        confirmCodeRepository.save(new ConfirmationCode(user, codeGenerator.generate()));
    }

    @Override
    public UserDetails loadUserByUsername(String email)  {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email: " + email + " is not found."));
    }

    @Transactional
    public String confirmEmail(String email, String code) {
        ConfirmationCode dbCode = confirmCodeRepository.findByUserEmailAndCode(email, code)
                .orElseThrow(() -> new ConfirmCodeException("Confirm code not found or expired"));

        User user = dbCode.getUser();
        user.setConfirmed(true);

        return jwtTokenService.generateToken(user);
    }
}
