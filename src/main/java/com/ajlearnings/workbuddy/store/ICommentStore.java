package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.Comment;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICommentStore {
    Comment add(Comment comment);
    List<Comment> getAllPerWorkItem(ObjectId workItemId);
    Comment get(ObjectId commentId);
    Comment update(Comment comment);
    void delete(ObjectId commentId);
    void deleteAllPerWorkItem(ObjectId workItemId);
}