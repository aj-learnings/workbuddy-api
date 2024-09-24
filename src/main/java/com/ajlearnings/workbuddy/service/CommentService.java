package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.entity.BaseEntity;
import com.ajlearnings.workbuddy.exception.ResourceNotFoundException;
import com.ajlearnings.workbuddy.model.request.CreateCommentRequest;
import com.ajlearnings.workbuddy.model.request.UpdateCommentRequest;
import com.ajlearnings.workbuddy.model.response.CommentResponse;
import com.ajlearnings.workbuddy.model.response.UserReactionResponse;
import com.ajlearnings.workbuddy.store.ICommentStore;
import com.ajlearnings.workbuddy.store.IUserStore;
import com.ajlearnings.workbuddy.store.IWorkItemStore;
import com.ajlearnings.workbuddy.translator.CommentTranslator;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@CacheConfig(cacheNames = "comment")
public class CommentService implements ICommentService {

    private final IWorkItemStore workItemStore;
    private final ICommentStore commentStore;
    private final IUserStore userStore;
    private final IUserReactionService userReactionService;

    public CommentService(IWorkItemStore workItemStore, ICommentStore commentStore, IUserStore userStore, IUserReactionService userReactionService) {
        this.workItemStore = workItemStore;
        this.commentStore = commentStore;
        this.userStore = userStore;
        this.userReactionService = userReactionService;
    }

    @Override
    @CacheEvict(key = "#workItemId + '_all'")
    public CommentResponse addComment(ObjectId workItemId, CreateCommentRequest createCommentRequest) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        var user = userStore.getByUsername(username);
        var workItem = workItemStore.get(workItemId);
        var comment = CommentTranslator.ToEntity(createCommentRequest);
        comment.setWorkItem(workItem);
        comment.setUser(user);
        var addedComment = commentStore.add(comment);
        workItemStore.update(workItem);
        return CommentTranslator.ToResponse(addedComment);
    }

    @Override
    public List<CommentResponse> getAllCommentsPerWorkItem(ObjectId workItemId) {
        var workItem = workItemStore.get(workItemId);
        var comments = commentStore.getAllPerWorkItem(workItemId);
        comments.sort(Comparator.comparing(BaseEntity::getCreatedAt));
        var commentsId = comments.stream().map(BaseEntity::getId).toList();
        var userReactions = userReactionService.getAllUserReactionsForAllComments(commentsId);
        var userReactionsGroupedByCommentId = userReactions.stream().collect(Collectors.groupingBy(UserReactionResponse::getCommentId));
        return comments.stream()
                       .map(comment -> CommentTranslator.ToResponseWithUserReactions(comment, Objects.requireNonNullElse(userReactionsGroupedByCommentId.get(comment.getId().toString()), List.of()))).toList();
    }

    @Override
    @CacheEvict(key = "#workItemId + '_all'")
    public CommentResponse updateComment(ObjectId workItemId, ObjectId commentId, UpdateCommentRequest updateCommentRequest) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        var user = userStore.getByUsername(username);
        var workItem = workItemStore.get(workItemId);
        var comment = commentStore.get(commentId);
        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new AccessDeniedException("You do not have permission to update this comment");
        }
        if (!comment.getWorkItem().getId().toString().equals(workItem.getId().toString())) {
            throw new ResourceNotFoundException("Comment does not exist in given workitem.");
        }
        comment.setText(updateCommentRequest.getText());
        var updatedComment = commentStore.update(comment);
        workItemStore.update(workItem);
        return CommentTranslator.ToResponse(updatedComment);
    }

    @Override
    @CacheEvict(key = "#workItemId + '_all'")
    public boolean deleteComment(ObjectId workItemId, ObjectId commentId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        var user = userStore.getByUsername(username);
        var workItem = workItemStore.get(workItemId);
        var comment = commentStore.get(commentId);
        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new AccessDeniedException("You do not have permission to delete this comment");
        }
        if (!comment.getWorkItem().getId().toString().equals(workItem.getId().toString())) {
            throw new ResourceNotFoundException("Comment does not exist in given workitem.");
        }
        commentStore.delete(commentId);
        workItemStore.update(workItem);
        return true;
    }

    @Override
    public void deleteAllCommentsPerWorkItem(ObjectId workItemId) {
        commentStore.deleteAllPerWorkItem(workItemId);
    }
}
