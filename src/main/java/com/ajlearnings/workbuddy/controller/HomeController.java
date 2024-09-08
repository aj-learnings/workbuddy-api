package com.ajlearnings.workbuddy.controller;

import com.ajlearnings.workbuddy.model.request.CreateUserRequest;
import com.ajlearnings.workbuddy.model.request.LoginRequest;
import com.ajlearnings.workbuddy.model.response.LoginResponse;
import com.ajlearnings.workbuddy.service.IUserService;
import com.ajlearnings.workbuddy.service.JwtService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Hidden
@RestController
@RequestMapping("/")
public class HomeController {

    private final ServletContext servletContext;
    private final IUserService userService;
    private final JwtService jwtService;

    public HomeController(ServletContext servletContext, IUserService userService, JwtService jwtService) {
        this.servletContext = servletContext;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public RedirectView redirectToSwaggerUI() {
        String contextPath = servletContext.getContextPath();
        String swaggerUrl = contextPath + "/swagger-ui/index.html";
        return new RedirectView(swaggerUrl);
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody CreateUserRequest createUserRequest) {
        var createdUser = userService.addUser(createUserRequest);
        var loginRequest = new LoginRequest();
        loginRequest.setUserName(createdUser.getUsername());
        loginRequest.setPassword(createUserRequest.getPassword());
        return authenticate(loginRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        var authenticatedUser = userService.authenticateUser(loginRequest);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        var loginResponse = LoginResponse.builder().token(jwtToken).build();
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
