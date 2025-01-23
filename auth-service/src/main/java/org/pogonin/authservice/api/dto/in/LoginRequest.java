package org.pogonin.authservice.api.dto.in;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
