package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.request.LoginRequest;
import com.ajlearnings.workbuddy.model.response.LoginResponse;
import com.ajlearnings.workbuddy.model.response.UserResponse;
import com.ajlearnings.workbuddy.translator.UserTranslator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IUserService userService;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService, IUserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserNameOrEmail(),
                        loginRequest.getPassword()));
        var authenticatedUser = userService.getUserByUserNameOrEmail(loginRequest.getUserNameOrEmail());
        String jwtToken = jwtService.generateToken(authenticatedUser);
        return LoginResponse.builder().token(jwtToken).build();
    }

    @Override
    public UserResponse verify() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userName = authentication.getName();
        var user = userService.getUserByUserNameOrEmail(userName);
        return UserTranslator.ToResponse(user);
    }
}
