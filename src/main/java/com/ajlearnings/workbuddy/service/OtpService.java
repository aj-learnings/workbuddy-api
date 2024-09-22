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
    public long generateOTP(String uniqueIdentifier, int digits) {
        return (long) Math.pow(10, digits-1) + random.nextLong((long) (9 * Math.pow(10, digits-1)));
    }

    @Override
    @Cacheable(key = "#uniqueIdentifier")
    public long getOTP(String uniqueIdentifier) {
        throw new ResourceNotFoundException("Please generate OTP again.");
    }
}
