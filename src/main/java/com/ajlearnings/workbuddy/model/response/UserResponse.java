package com.ajlearnings.workbuddy.model.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String userName;
    private String email;
    private Boolean isVerified;
}
