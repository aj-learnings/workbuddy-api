package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.Constants;
import com.ajlearnings.workbuddy.exception.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
@CacheConfig(cacheNames = "otp")
public class OtpService implements IOtpService {

    private static final Random random = new SecureRandom();

    @Override
    @CachePut(key = "#uniqueIdentifier")
    public long generateOTP(String uniqueIdentifier, int digits) {
        var builder = new StringBuilder();
        for (int i=0; i<digits; i++) {
            builder.append(Constants.NUMBERS.charAt(random.nextInt(Constants.NUMBERS.length())));
        }
        return Long.parseLong(builder.toString());
    }

    @Override
    @Cacheable(key = "#uniqueIdentifier")
    public long getOTP(String uniqueIdentifier) {
        throw new ResourceNotFoundException("Please generate OTP again.");
    }
}
