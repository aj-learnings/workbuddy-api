package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.response.UserReactionResponse;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserReactionService {
    UserReactionResponse addUserReactionForComment(ObjectId commentId, boolean isLiked);
    UserReactionResponse updateUserReaction(ObjectId userReactionId, boolean isLiked);
    List<UserReactionResponse> getAllUserReactionsForAllComments(List<ObjectId> commentsId);
    boolean deleteUserReaction(ObjectId userReactionId);
    void deleteAllUserReactionsForAllComments(List<ObjectId> commentsId);
}
