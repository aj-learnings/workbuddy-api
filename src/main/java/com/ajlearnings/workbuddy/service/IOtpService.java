package com.ajlearnings.workbuddy.service;

import org.springframework.stereotype.Service;

@Service
public interface IOtpService {
    int generateOTP(String uniqueIdentifier);
    int getOTP(String uniqueIdentifier);
}
