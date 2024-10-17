package com.ajlearnings.workbuddy.service.kafka.consumers;

import com.ajlearnings.workbuddy.Constants;
import com.ajlearnings.workbuddy.helpers.EmailTemplates;
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

    @KafkaListener(topics = Constants.Kafka.Topics.UserReaction, groupId = "workbuddy-consumers")
    public void sendEmail(UserReactionDetails userReactionDetails) {
        var emailData = EmailData.builder()
                                 .to(userReactionDetails.getOwnerEmail())
                                 .subject(EmailTemplates.UserReaction.Subject)
                                 .body(String.format(EmailTemplates.UserReaction.Body,
                                                        userReactionDetails.getOwnerName(),
                                                        userReactionDetails.getReactedBy(),
                                                        userReactionDetails.getIsLiked() ? "liked": "disliked",
                                                        userReactionDetails.getText()))
                                 .build();
        emailService.sendEmail(emailData);
    }
}
