package com.ajlearnings.workbuddy.model.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private String id;
    private String type;
    private String text;
    private Date created;
    private Date updated;
    private String createdBy;
    private List<UserReactionResponse> userReactions = List.of();
}
