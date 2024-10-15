package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.Constants;
import com.ajlearnings.workbuddy.exception.UserAlreadyExistException;
import com.ajlearnings.workbuddy.model.EmailData;
import com.ajlearnings.workbuddy.model.request.EmailVerificationRequest;
import com.ajlearnings.workbuddy.store.IUserStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailVerificationService implements IEmailVerificationService {

    @Value("${spring.cache.time-to-live.otp}")
    private int otpValidity;

    private static final Random random = new Random();
    private final IOtpService otpService;
    private final IUserStore userStore;
    private final IEmailService emailService;

    public EmailVerificationService(IOtpService otpService, IUserStore userStore, IEmailService emailService) {
        this.otpService = otpService;
        this.userStore = userStore;
        this.emailService = emailService;
    }

    @Override
    public boolean generateOTPAndSendEmail() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        var user = userStore.getByUsername(username);
        if (user.getIsVerified()) {
            throw new UserAlreadyExistException("User is already verified");
        }
        var otp = otpService.generateOTP(user.getEmail(), 4);
        var emailData = EmailData.builder()
                                 .to(user.getEmail())
                                 .subject(Constants.Email.OTP.Subject)
                                 .body(String.format(Constants.Email.OTP.Body, otp, otpValidity))
                                 .build();
        return emailService.sendEmail(emailData);
    }

    @Override
    public boolean verifyEmail(EmailVerificationRequest emailVerificationRequest) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        var user = userStore.getByUsername(username);
        var otp = otpService.getOTP(user.getEmail());
        if (user.getIsVerified()) {
            throw new UserAlreadyExistException("User is already verified");
        }
        if (otp == emailVerificationRequest.getOtp()) {
            user.setIsVerified(true);
            userStore.update(user);
            return true;
        }
        return false;
    }
}
