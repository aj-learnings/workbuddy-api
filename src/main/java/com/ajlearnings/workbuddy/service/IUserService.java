package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.entity.User;
import com.ajlearnings.workbuddy.model.request.CreateUserRequest;
import com.ajlearnings.workbuddy.model.request.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    User addUser(CreateUserRequest createUserRequest);
    User authenticateUser(LoginRequest loginRequest);
}
