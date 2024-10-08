package com.ajlearnings.workbuddy.translator;

import com.ajlearnings.workbuddy.entity.Comment;
import com.ajlearnings.workbuddy.enums.CommentType;
import com.ajlearnings.workbuddy.model.request.CreateCommentRequest;
import com.ajlearnings.workbuddy.model.response.CommentResponse;
import com.ajlearnings.workbuddy.model.response.UserReactionResponse;

import java.util.List;

public class CommentTranslator {
    public static Comment ToEntity(CreateCommentRequest createCommentRequest) {
        return Comment.builder()
                        .type(CommentType.GENERAL)
                        .text(createCommentRequest.getText())
                        .build();
    }

    public static CommentResponse ToResponse(Comment comment) {
        return CommentResponse.builder()
                                .text(comment.getText())
                                .type(comment.getType().toString())
                                .id(comment.getId().toString())
                                .created(comment.getCreatedAt())
                                .updated(comment.getUpdatedAt())
                                .createdBy(comment.getUser().getUsername())
                                .build();
    }

    public static CommentResponse ToResponseWithUserReactions(Comment comment, List<UserReactionResponse> userReactionsResponse) {
        var response = ToResponse(comment);
        response.setUserReactions(userReactionsResponse);
        return response;
    }
}
