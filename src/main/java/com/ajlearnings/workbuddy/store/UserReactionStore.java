package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.UserReaction;
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
    public List<UserReaction> getAllForComments(List<ObjectId> commentsId) {
        return userReactionRepository.findAllByCommentIn(commentsId);
    }

    @Override
    public void deleteAllForComments(List<ObjectId> commentsId) {
        userReactionRepository.deleteAllByCommentIn(commentsId);
    }
}
