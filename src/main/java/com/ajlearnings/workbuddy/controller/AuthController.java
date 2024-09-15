package com.ajlearnings.workbuddy.controller;

import com.ajlearnings.workbuddy.model.request.CreateUserRequest;
import com.ajlearnings.workbuddy.model.request.LoginRequest;
import com.ajlearnings.workbuddy.model.response.LoginResponse;
import com.ajlearnings.workbuddy.model.response.UserResponse;
import com.ajlearnings.workbuddy.service.AuthenticationService;
import com.ajlearnings.workbuddy.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Validated
public class AuthController {

    private final IUserService userService;
    private final AuthenticationService authenticationService;

    public AuthController(IUserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody CreateUserRequest createUserRequest) {
        var user = userService.addUser(createUserRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        var loginResponse = authenticationService.authenticateUser(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<UserResponse> verify() {
        var userResponse = authenticationService.verify();
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
