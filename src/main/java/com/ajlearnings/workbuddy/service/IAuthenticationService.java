package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.request.LoginRequest;
import com.ajlearnings.workbuddy.model.response.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface IAuthenticationService {
    LoginResponse authenticateUser(LoginRequest loginRequest);
}
