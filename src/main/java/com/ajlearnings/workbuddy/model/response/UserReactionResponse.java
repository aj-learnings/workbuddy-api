package com.ajlearnings.workbuddy.model.response;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReactionResponse {
    private boolean isLiked;
    private String reactedBy;
    private String commentId;
}
