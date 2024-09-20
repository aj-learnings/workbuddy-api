package com.ajlearnings.workbuddy.model.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReactionResponse {
    private String id;
    private Boolean isLiked;
    private String reactedBy;
    private String commentId;
}
