package com.ajlearnings.workbuddy.controller;

import com.ajlearnings.workbuddy.annotation.ValidObjectId;
import com.ajlearnings.workbuddy.model.request.CreateCommentRequest;
import com.ajlearnings.workbuddy.model.request.UpdateCommentRequest;
import com.ajlearnings.workbuddy.model.response.CommentResponse;
import com.ajlearnings.workbuddy.service.ICommentService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workitem/id/{workItemId}/comment")
@Validated
public class CommentController {

    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@PathVariable("workItemId") @ValidObjectId String workItemId, @Valid @RequestBody CreateCommentRequest createCommentRequest) {
        var addedComment = commentService.addComment(new ObjectId(workItemId), createCommentRequest);
        return new ResponseEntity<>(addedComment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllCommentsPerWorkItem(@PathVariable("workItemId") @ValidObjectId String workItemId) {
        var comments = commentService.getAllCommentsPerWorkItem(new ObjectId(workItemId));
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable("workItemId") @ValidObjectId String workItemId, @PathVariable("id") @ValidObjectId String commentId, @Valid @RequestBody UpdateCommentRequest updateCommentRequest) {
        var updatedComment = commentService.updateComment(new ObjectId(workItemId), new ObjectId(commentId), updateCommentRequest);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable("workItemId") @ValidObjectId String workItemId, @PathVariable("id") @ValidObjectId String commentId) {
        boolean isDeleted = commentService.deleteComment(new ObjectId(workItemId), new ObjectId(commentId));
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
}
