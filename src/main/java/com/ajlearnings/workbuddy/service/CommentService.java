package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.entity.BaseEntity;
import com.ajlearnings.workbuddy.exception.ResourceNotFoundException;
import com.ajlearnings.workbuddy.model.request.CreateCommentRequest;
import com.ajlearnings.workbuddy.model.request.UpdateCommentRequest;
import com.ajlearnings.workbuddy.model.response.CommentResponse;
import com.ajlearnings.workbuddy.store.ICommentStore;
import com.ajlearnings.workbuddy.store.IUserStore;
import com.ajlearnings.workbuddy.store.IWorkItemStore;
import com.ajlearnings.workbuddy.translator.CommentTranslator;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class CommentService implements ICommentService {

    private final IWorkItemStore workItemStore;
    private final ICommentStore commentStore;
    private final IUserStore userStore;

    public CommentService(IWorkItemStore workItemStore, ICommentStore commentStore, IUserStore userStore) {
        this.workItemStore = workItemStore;
        this.commentStore = commentStore;
        this.userStore = userStore;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "comment", key = "#workItemId + '_all'"),
            @CacheEvict(value = "workitem", key = "#workItemId"),
            @CacheEvict(value = "workitem", key = "'all'")
    })
    public CommentResponse addComment(ObjectId workItemId, CreateCommentRequest createCommentRequest) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userName = authentication.getName();
        var user = userStore.getByUserName(userName);
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
        return comments.stream().map(CommentTranslator::ToResponse).toList();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "comment", key = "#workItemId + '_all'"),
            @CacheEvict(value = "workitem", key = "#workItemId"),
            @CacheEvict(value = "workitem", key = "'all'")
    })
    public CommentResponse updateComment(ObjectId workItemId, ObjectId commentId, UpdateCommentRequest updateCommentRequest) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userName = authentication.getName();
        var user = userStore.getByUserName(userName);
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
    @Caching(evict = {
            @CacheEvict(value = "comment", key = "#workItemId + '_all'"),
            @CacheEvict(value = "workitem", key = "#workItemId"),
            @CacheEvict(value = "workitem", key = "'all'")
    })
    public boolean deleteComment(ObjectId workItemId, ObjectId commentId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userName = authentication.getName();
        var user = userStore.getByUserName(userName);
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
    @Caching(evict = {
            @CacheEvict(value = "comment", key = "#workItemId + '_all'"),
            @CacheEvict(value = "workitem", key = "#workItemId"),
            @CacheEvict(value = "workitem", key = "'all'")
    })
    public void deleteAllCommentsPerWorkItem(ObjectId workItemId) {
        commentStore.deleteAllPerWorkItem(workItemId);
    }
}
