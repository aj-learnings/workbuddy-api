package com.ajlearnings.workbuddy.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Username or Email should not be blank")
    @NotNull
    private String userNameOrEmail;
    @NotBlank(message = "Password should not be blank")
    @NotNull
    private String password;
}
