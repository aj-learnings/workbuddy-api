package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.entity.User;
import com.ajlearnings.workbuddy.model.request.CreateUserRequest;
import com.ajlearnings.workbuddy.model.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    UserResponse addUser(CreateUserRequest createUserRequest);
    User getUserByUserName(String userName);
}
