package com.ajlearnings.workbuddy.translator;

import com.ajlearnings.workbuddy.entity.UserReaction;
import com.ajlearnings.workbuddy.model.request.CreateUserReactionRequest;
import com.ajlearnings.workbuddy.model.response.UserReactionResponse;

public class UserReactionTranslator {
    public static UserReaction ToEntity(CreateUserReactionRequest createUserReactionRequest) {
        return UserReaction.builder()
                           .isLiked(createUserReactionRequest.getIsLiked())
                           .build();
    }

    public static UserReactionResponse ToResponse(UserReaction userReaction) {
        return UserReactionResponse.builder()
                                   .id(userReaction.getId().toString())
                                   .isLiked(userReaction.getIsLiked())
                                   .reactedBy(userReaction.getUser().getUsername())
                                   .commentId(userReaction.getComment().getId().toString())
                                   .build();
    }
}
