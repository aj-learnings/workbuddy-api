package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.Comment;
import com.ajlearnings.workbuddy.exception.ResourceNotFoundException;
import com.ajlearnings.workbuddy.repository.ICommentRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentStore implements ICommentStore {

    @Autowired
    private ICommentRepository commentRepository;

    @Override
    public Comment add(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllPerWorkItem(ObjectId workItemId) {
        return commentRepository.findAllByWorkItem(workItemId);
    }

    @Override
    public Comment get(ObjectId commentId) {
        return commentRepository.findById(commentId)
                                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id : " + commentId.toString()));
    }

    @Override
    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(ObjectId commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void deleteAllPerWorkItem(ObjectId workItemId) {
        commentRepository.deleteAllByWorkItem(workItemId);
    }
}
