package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.request.CreateUserReactionRequest;
import com.ajlearnings.workbuddy.model.request.UpdateUserReactionRequest;
import com.ajlearnings.workbuddy.model.response.UserReactionResponse;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserReactionService {
    UserReactionResponse addUserReactionForComment(ObjectId commentId, CreateUserReactionRequest createUserReactionRequest);
    UserReactionResponse updateUserReaction(ObjectId userReactionId, UpdateUserReactionRequest updateUserReactionRequest);
    List<UserReactionResponse> getAllUserReactionsForAllComments(List<ObjectId> commentsId);
    boolean deleteUserReaction(ObjectId userReactionId);
    void deleteAllUserReactionsForAllComments(List<ObjectId> commentsId);
}
