package com.ajlearnings.workbuddy.model.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationRequest {
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String emailId;
    @NotNull(message = "OTP cannot be null")
    @Min(value = 1000, message = "OTP must be a 4-digit number")
    @Max(value = 9999, message = "OTP must be a 4-digit number")
    private long otp;
}
