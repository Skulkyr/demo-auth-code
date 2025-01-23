package org.pogonin.authservice.core.service.impl;

import org.pogonin.authservice.core.service.CodeGenerator;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class SecureRandomCodeGenerator implements CodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++)
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        return sb.toString();
    }
}
