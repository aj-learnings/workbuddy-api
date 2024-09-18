package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.UserReaction;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserReactionStore {
    List<UserReaction> getAllForComments(List<ObjectId> commentsId);
    void deleteAllForComments(List<ObjectId> commentsId);
}
