package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.request.EmailVerificationRequest;
import org.springframework.stereotype.Service;

@Service
public interface IEmailVerificationService {
    boolean generateOTPAndSendEmail();
    boolean verifyEmail(EmailVerificationRequest emailVerificationRequest);
}
