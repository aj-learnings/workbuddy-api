package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.exception.ResourceNotFoundException;
import com.ajlearnings.workbuddy.model.request.CreateCommentRequest;
import com.ajlearnings.workbuddy.model.request.UpdateCommentRequest;
import com.ajlearnings.workbuddy.model.response.CommentResponse;
import com.ajlearnings.workbuddy.store.ICommentStore;
import com.ajlearnings.workbuddy.store.IWorkItemStore;
import com.ajlearnings.workbuddy.translator.CommentTranslator;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommentService implements ICommentService {

    @Autowired
    private IWorkItemStore workItemStore;
    @Autowired
    private ICommentStore commentStore;

    @Override
    @Caching(evict = {
            @CacheEvict(value = "comment", key = "#workItemId + '_all'"),
            @CacheEvict(value = "workitem", key = "#workItemId")
    })
    public CommentResponse addComment(ObjectId workItemId, CreateCommentRequest createCommentRequest) {
        var workItem = workItemStore.get(workItemId);
        var comment = CommentTranslator.ToEntity(createCommentRequest);
        comment.setWorkItem(workItem);
        var addedComment = commentStore.add(comment);
        return CommentTranslator.ToResponse(addedComment);
    }

    @Override
    @Cacheable(value = "comment", key = "#workItemId + '_all'")
    public List<CommentResponse> getAllCommentsPerWorkItem(ObjectId workItemId) {
        var comments = commentStore.getAllPerWorkItem(workItemId);
        return comments.stream().map(CommentTranslator::ToResponse).toList();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "comment", key = "#workItemId + '_all'"),
            @CacheEvict(value = "workitem", key = "#workItemId")
    })
    public CommentResponse updateComment(ObjectId workItemId, ObjectId commentId, UpdateCommentRequest updateCommentRequest) {
        var workItem = workItemStore.get(workItemId);
        var comment = commentStore.get(commentId);
        if (!comment.getWorkItem().getId().toString().equals(workItem.getId().toString())) {
            throw new ResourceNotFoundException("Comment does not exist in given workitem.");
        }
        comment.setText(updateCommentRequest.getText());
        var updatedComment = commentStore.update(comment);
        return CommentTranslator.ToResponse(updatedComment);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "comment", key = "#workItemId + '_all'"),
            @CacheEvict(value = "workitem", key = "#workItemId")
    })
    public boolean deleteComment(ObjectId workItemId, ObjectId commentId) {
        var workItem = workItemStore.get(workItemId);
        var comment = commentStore.get(commentId);
        if (!comment.getWorkItem().getId().toString().equals(workItem.getId().toString())) {
            throw new ResourceNotFoundException("Comment does not exist in given workitem.");
        }
        commentStore.delete(commentId);
        return true;
    }

    @Override
    @CacheEvict(value = "comment", key = "#workItemId + '_all'")
    public void deleteAllCommentsPerWorkItem(ObjectId workItemId) {
        commentStore.deleteAllPerWorkItem(workItemId);
    }
}
