package com.ajlearnings.workbuddy.translator;

import com.ajlearnings.workbuddy.entity.User;
import com.ajlearnings.workbuddy.model.request.CreateUserRequest;
import com.ajlearnings.workbuddy.model.response.UserResponse;

public class UserTranslator {
    public static User ToEntity(CreateUserRequest createUserRequest) {
        return User.builder()
                    .userName(createUserRequest.getUserName())
                    .password(createUserRequest.getPassword())
                    .build();
    }

    public static UserResponse ToResponse(User user) {
        return UserResponse.builder()
                            .userName(user.getUsername())
                            .build();
    }
}
