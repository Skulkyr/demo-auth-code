package org.pogonin.confirmationservice.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ConfirmCode {
    private long id;
    private String code;
    @JsonProperty("user_email")
    private String userEmail;
    @JsonProperty("create_time")
    private OffsetDateTime createTime;
}
