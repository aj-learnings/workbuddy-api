package com.ajlearnings.workbuddy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReactionDetails {
    private String reactedBy;
    private String ownerEmail;
    private Boolean isLiked;
}
