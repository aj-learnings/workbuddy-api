package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.UserReaction;
import com.ajlearnings.workbuddy.exception.ResourceNotFoundException;
import com.ajlearnings.workbuddy.repository.IUserReactionRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReactionStore implements IUserReactionStore {

    private final IUserReactionRepository userReactionRepository;

    public UserReactionStore(IUserReactionRepository userReactionRepository) {
        this.userReactionRepository = userReactionRepository;
    }

    @Override
    public UserReaction add(UserReaction userReaction) {
        return userReactionRepository.save(userReaction);
    }

    @Override
    public UserReaction get(ObjectId userReactionId) {
        return userReactionRepository.findById(userReactionId)
                                     .orElseThrow(() -> new ResourceNotFoundException("User Reaction not found with id : " + userReactionId));
    }

    @Override
    public List<UserReaction> getAllForComments(List<ObjectId> commentsId) {
        return userReactionRepository.findAllByCommentIn(commentsId);
    }

    @Override
    public UserReaction update(UserReaction userReaction) {
        return userReactionRepository.save(userReaction);
    }

    @Override
    public void delete(ObjectId userReactionId) {
        userReactionRepository.deleteById(userReactionId);
    }

    @Override
    public void deleteAllForComments(List<ObjectId> commentsId) {
        userReactionRepository.deleteAllByCommentIn(commentsId);
    }
}
