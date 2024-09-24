package com.ajlearnings.workbuddy.controller;

import com.ajlearnings.workbuddy.model.request.EmailVerificationRequest;
import com.ajlearnings.workbuddy.service.IEmailVerificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email-verification")
@Validated
public class EmailVerificationController {

    private final IEmailVerificationService emailVerificationService;

    public EmailVerificationController(IEmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @GetMapping
    public ResponseEntity<Boolean> generateOTP() {
        var emailSend = emailVerificationService.generateOTPAndSendEmail();
        return new ResponseEntity<>(emailSend, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Boolean> verifyOTP(@Valid @RequestBody EmailVerificationRequest emailVerificationRequest) {
        var isOTPValid = emailVerificationService.verifyEmail(emailVerificationRequest);
        return new ResponseEntity<>(isOTPValid, HttpStatus.OK);
    }
}
