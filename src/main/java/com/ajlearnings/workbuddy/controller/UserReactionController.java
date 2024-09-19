package com.ajlearnings.workbuddy.controller;

import com.ajlearnings.workbuddy.annotation.ValidObjectId;
import com.ajlearnings.workbuddy.model.request.CreateUserReactionRequest;
import com.ajlearnings.workbuddy.model.request.UpdateUserReactionRequest;
import com.ajlearnings.workbuddy.model.response.UserReactionResponse;
import com.ajlearnings.workbuddy.service.IUserReactionService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@Validated
public class UserReactionController {

    private final IUserReactionService userReactionService;

    public UserReactionController(IUserReactionService userReactionService) {
        this.userReactionService = userReactionService;
    }

    @PostMapping("/comment/id/{commentId}/reaction")
    public ResponseEntity<UserReactionResponse> addReactionForComment(@PathVariable("commentId") @ValidObjectId String commentId, @Valid @RequestBody CreateUserReactionRequest createUserReactionRequest) {
        var addedReaction = userReactionService.addUserReactionForComment(new ObjectId(commentId), createUserReactionRequest);
        return new ResponseEntity<>(addedReaction, HttpStatus.CREATED);
    }

    @PutMapping("/reaction/id/{reactionId}")
    public ResponseEntity<UserReactionResponse> updateReaction(@PathVariable("reactionId") @ValidObjectId String userReactionId, @Valid @RequestBody UpdateUserReactionRequest updateUserReactionRequest) {
        var updatedUserReaction = userReactionService.updateUserReaction(new ObjectId(userReactionId), updateUserReactionRequest);
        return new ResponseEntity<>(updatedUserReaction, HttpStatus.OK);
    }

    @DeleteMapping("/reaction/id/{reactionId}")
    public ResponseEntity<Boolean> deleteReaction(@PathVariable("reactionId") @ValidObjectId String userReactionId) {
        boolean isDeleted = userReactionService.deleteUserReaction(new ObjectId(userReactionId));
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
}
