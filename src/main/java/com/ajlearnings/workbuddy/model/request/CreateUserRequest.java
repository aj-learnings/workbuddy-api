package com.ajlearnings.workbuddy.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "Username should not be blank")
    @NotNull
    private String username;
    @NotBlank(message = "Password should not be blank")
    @NotNull
    private String password;
    @Email(message = "Email should be valid")
    private String email;
}
