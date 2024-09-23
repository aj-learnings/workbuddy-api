package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.EmailData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService implements IEmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public boolean sendEmail(EmailData emailData) {
        try {
            var message = javaMailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message);
            helper.setFrom(fromEmail);
            helper.setTo(emailData.getTo());
            helper.setSubject(emailData.getSubject());
            helper.setText(emailData.getBody());
            javaMailSender.send(message);
            return true;
        } catch(Exception ex) {
            log.error("Email not sent", ex);
            return false;
        }
    }
}

