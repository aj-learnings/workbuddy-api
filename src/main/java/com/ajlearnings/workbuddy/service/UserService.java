package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.entity.User;
import com.ajlearnings.workbuddy.model.request.CreateUserRequest;
import com.ajlearnings.workbuddy.model.request.LoginRequest;
import com.ajlearnings.workbuddy.store.IUserStore;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final IUserStore userStore;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(IUserStore userStore, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userStore = userStore;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User addUser(CreateUserRequest createUserRequest) {
        var user = User.builder()
                        .userName(createUserRequest.getUserName())
                        .password(passwordEncoder.encode(createUserRequest.getPassword()))
                        .email(createUserRequest.getEmail())
                        .build();

        return userStore.add(user);
    }

    @Override
    public User authenticateUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                                                        loginRequest.getPassword()));
        return userStore.getByUserName(loginRequest.getUserName()).orElseThrow();
    }
}
