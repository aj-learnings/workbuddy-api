package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.request.CreateCommentRequest;
import com.ajlearnings.workbuddy.model.request.UpdateCommentRequest;
import com.ajlearnings.workbuddy.model.response.CommentResponse;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICommentService {
    CommentResponse addComment(ObjectId workItemId, CreateCommentRequest createCommentRequest);
    List<CommentResponse> getAllCommentsPerWorkItem(ObjectId workItemId);
    CommentResponse updateComment(ObjectId workItemId, ObjectId commentId, UpdateCommentRequest updateCommentRequest);
    boolean deleteComment(ObjectId workItemId, ObjectId commentId);
    void deleteAllCommentsPerWorkItem(ObjectId workItemId);
}
