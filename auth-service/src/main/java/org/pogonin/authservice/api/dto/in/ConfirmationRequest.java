package org.pogonin.authservice.api.dto.in;

import lombok.Data;

@Data
public class ConfirmationRequest {
    private String email;
    private String code;
}
