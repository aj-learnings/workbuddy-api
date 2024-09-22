package com.ajlearnings.workbuddy.controller;

import com.ajlearnings.workbuddy.model.request.EmailOTPRequest;
import com.ajlearnings.workbuddy.model.request.EmailVerificationRequest;
import com.ajlearnings.workbuddy.service.IEmailVerificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email-verification")
@Validated
public class EmailVerificationController {

    private final IEmailVerificationService emailVerificationService;

    public EmailVerificationController(IEmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping("/otp")
    public ResponseEntity<Void> generateOTP(@Valid @RequestBody EmailOTPRequest emailOTPRequest) {
        emailVerificationService.generateOTPAndSendEmail(emailOTPRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyOTP(@Valid @RequestBody EmailVerificationRequest emailVerificationRequest) {
        var isOTPValid = emailVerificationService.verifyEmail(emailVerificationRequest);
        return new ResponseEntity<Boolean>(isOTPValid, HttpStatus.OK);
    }
}
