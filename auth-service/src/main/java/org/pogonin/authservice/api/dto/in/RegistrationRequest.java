package org.pogonin.authservice.api.dto.in;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String email;
    private String password;
    private String name;
}
