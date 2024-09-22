package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.request.EmailOTPRequest;
import com.ajlearnings.workbuddy.model.request.EmailVerificationRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailVerificationService implements IEmailVerificationService {

    private static final Random random = new Random();
    private final IOtpService otpService;

    public EmailVerificationService(IOtpService otpService) {
        this.otpService = otpService;
    }

    @Override
    public void generateOTPAndSendEmail(EmailOTPRequest emailOTPRequest) {
        var otp = otpService.generateOTP(emailOTPRequest.getEmailId(), 4);
        // email send logic here
    }

    @Override
    public boolean verifyEmail(EmailVerificationRequest emailVerificationRequest) {
        var otp = otpService.getOTP(emailVerificationRequest.getEmailId());
        return otp == emailVerificationRequest.getOtp();
    }
}
