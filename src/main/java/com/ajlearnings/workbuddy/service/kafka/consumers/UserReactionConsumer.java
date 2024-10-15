package com.ajlearnings.workbuddy.service.kafka.consumers;

import com.ajlearnings.workbuddy.Constants;
import com.ajlearnings.workbuddy.model.EmailData;
import com.ajlearnings.workbuddy.model.UserReactionDetails;
import com.ajlearnings.workbuddy.service.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserReactionConsumer {

    private final IEmailService emailService;

    public UserReactionConsumer(IEmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "user-reaction-notification", groupId = "workbuddy-consumers")
    public void sendEmail(UserReactionDetails userReactionDetails) {
        var emailData = EmailData.builder()
                                 .to(userReactionDetails.getOwnerEmail())
                                 .subject(Constants.UserReaction.Subject)
                                 .body(String.format(Constants.UserReaction.Body, userReactionDetails.getReactedBy(), userReactionDetails.getIsLiked() ? "liked": "disliked"))
                                 .build();
        emailService.sendEmail(emailData);
    }
}
