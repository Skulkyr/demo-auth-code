package org.pogonin.authservice.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.pogonin.authservice.core.exception.UserNotFoundException;
import org.pogonin.authservice.db.entity.User;
import org.pogonin.authservice.db.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)  {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email: " + email + " is not found."));
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
