package com.ajlearnings.workbuddy.translator;

import com.ajlearnings.workbuddy.entity.UserReaction;
import com.ajlearnings.workbuddy.model.request.CreateUserReactionRequest;
import com.ajlearnings.workbuddy.model.response.UserReactionResponse;

public class UserReactionTranslator {
    public static UserReaction ToEntity(CreateUserReactionRequest createUserReactionRequest) {
        return UserReaction.builder()
                           .isLiked(createUserReactionRequest.isLiked())
                           .build();
    }

    public static UserReactionResponse ToResponse(UserReaction userReaction) {
        return UserReactionResponse.builder()
                                   .isLiked(userReaction.isLiked())
                                   .reactedBy(userReaction.getUser().getUsername())
                                   .commentId(userReaction.getComment().getId().toString())
                                   .build();
    }
}
