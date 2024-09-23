package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.EmailData;
import org.springframework.stereotype.Service;

@Service
public interface IEmailService {
    boolean sendEmail(EmailData emailData);
}
