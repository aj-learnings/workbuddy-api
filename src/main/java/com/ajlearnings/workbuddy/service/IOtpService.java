package com.ajlearnings.workbuddy.service;

import org.springframework.stereotype.Service;

@Service
public interface IOtpService {
    long generateOTP(String uniqueIdentifier, int digits);
    long getOTP(String uniqueIdentifier);
}
