package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.request.EmailOTPRequest;
import com.ajlearnings.workbuddy.model.request.EmailVerificationRequest;
import org.springframework.stereotype.Service;

@Service
public interface IEmailVerificationService {
    void generateOTPAndSendEmail(EmailOTPRequest emailOTPRequest);
    boolean verifyEmail(EmailVerificationRequest emailVerificationRequest);
}
