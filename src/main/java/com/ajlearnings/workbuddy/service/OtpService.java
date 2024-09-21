package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.exception.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@CacheConfig(cacheNames = "otp")
public class OtpService implements IOtpService {

    private static final Random random = new Random();

    @Override
    @CachePut(key = "#uniqueIdentifier")
    public int generateOTP(String uniqueIdentifier) {
        return 1000 + random.nextInt(9000);
    }

    @Override
    @Cacheable(key = "#uniqueIdentifier")
    public int getOTP(String uniqueIdentifier) {
        throw new ResourceNotFoundException("Please generate OTP again.");
    }
}
