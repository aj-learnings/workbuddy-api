package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.Constants;
import com.ajlearnings.workbuddy.exception.UserAlreadyExistException;
import com.ajlearnings.workbuddy.model.EmailData;
import com.ajlearnings.workbuddy.model.request.EmailOTPRequest;
import com.ajlearnings.workbuddy.model.request.EmailVerificationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailVerificationService implements IEmailVerificationService {

    @Value("${spring.cache.time-to-live.otp}")
    private int otpValidity;

    private static final Random random = new Random();
    private final IOtpService otpService;
    private final IUserService userService;
    private final IEmailService emailService;

    public EmailVerificationService(IOtpService otpService, IUserService userService, IEmailService emailService) {
        this.otpService = otpService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public boolean generateOTPAndSendEmail(EmailOTPRequest emailOTPRequest) {
        if (this.userService.existsByEmail(emailOTPRequest.getEmailId())) {
            throw new UserAlreadyExistException("Email already exist");
        }
        var otp = otpService.generateOTP(emailOTPRequest.getEmailId(), 4);
        var emailData = EmailData.builder()
                                 .to(emailOTPRequest.getEmailId())
                                 .subject(Constants.EmailOTP.Subject)
                                 .body(String.format(Constants.EmailOTP.Body, otp, otpValidity))
                                 .build();
        return emailService.sendEmail(emailData);
    }

    @Override
    public boolean verifyEmail(EmailVerificationRequest emailVerificationRequest) {
        if (this.userService.existsByEmail(emailVerificationRequest.getEmailId())) {
            throw new UserAlreadyExistException("Email already exist");
        }
        var otp = otpService.getOTP(emailVerificationRequest.getEmailId());
        return otp == emailVerificationRequest.getOtp();
    }
}
