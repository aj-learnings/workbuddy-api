package com.ajlearnings.workbuddy.model.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private Boolean isVerified;
}
