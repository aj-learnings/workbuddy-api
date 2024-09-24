package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.request.CreateUserReactionRequest;
import com.ajlearnings.workbuddy.model.request.UpdateUserReactionRequest;
import com.ajlearnings.workbuddy.model.response.UserReactionResponse;
import com.ajlearnings.workbuddy.store.ICommentStore;
import com.ajlearnings.workbuddy.store.IUserReactionStore;
import com.ajlearnings.workbuddy.store.IUserStore;
import com.ajlearnings.workbuddy.translator.UserReactionTranslator;
import org.bson.types.ObjectId;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReactionService implements IUserReactionService {

    private final ICommentStore commentStore;
    private final IUserStore userStore;
    private final IUserReactionStore userReactionStore;

    public UserReactionService(ICommentStore commentStore, IUserStore userStore, IUserReactionStore userReactionStore) {
        this.commentStore = commentStore;
        this.userStore = userStore;
        this.userReactionStore = userReactionStore;
    }

    @Override
    public UserReactionResponse addUserReactionForComment(ObjectId commentId, CreateUserReactionRequest createUserReactionRequest) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        var user = userStore.getByUsername(username);
        var comment = commentStore.get(commentId);
        var userReaction = UserReactionTranslator.ToEntity(createUserReactionRequest);
        userReaction.setUser(user);
        userReaction.setComment(comment);
        var addedUserReaction = userReactionStore.add(userReaction);
        return UserReactionTranslator.ToResponse(addedUserReaction);
    }

    @Override
    public UserReactionResponse updateUserReaction(ObjectId userReactionId, UpdateUserReactionRequest updateUserReactionRequest) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        var user = userStore.getByUsername(username);
        var userReaction = userReactionStore.get(userReactionId);
        if (!userReaction.getUser().getUsername().equals(user.getUsername())) {
            throw new AccessDeniedException("You do not have permission to update this reaction");
        }
        userReaction.setIsLiked(updateUserReactionRequest.getIsLiked());
        var updatedUserReaction = userReactionStore.update(userReaction);
        return UserReactionTranslator.ToResponse(updatedUserReaction);
    }

    @Override
    public List<UserReactionResponse> getAllUserReactionsForAllComments(List<ObjectId> commentsId) {
        var userReactions = userReactionStore.getAllForComments(commentsId);
        return userReactions.stream().map(UserReactionTranslator::ToResponse).toList();
    }

    @Override
    public boolean deleteUserReaction(ObjectId userReactionId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        var user = userStore.getByUsername(username);
        var userReaction = userReactionStore.get(userReactionId);
        if (!userReaction.getUser().getUsername().equals(user.getUsername())) {
            throw new AccessDeniedException("You do not have permission to delete this reaction");
        }
        userReactionStore.delete(userReactionId);
        return true;
    }

    @Override
    public void deleteAllUserReactionsForAllComments(List<ObjectId> commentsId) {
        userReactionStore.deleteAllForComments(commentsId);
    }
}
