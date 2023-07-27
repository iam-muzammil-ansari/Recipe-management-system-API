package com.geekster.Recipe_management_system_API.service;

import com.geekster.Recipe_management_system_API.model.Comment;
import com.geekster.Recipe_management_system_API.model.User;
import com.geekster.Recipe_management_system_API.repository.CommentRepo;
import com.geekster.Recipe_management_system_API.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    UserRepo userRepo;


    public String addComment(Comment comment) {
        comment.setCommentCreationTimeStamp(LocalDateTime.now());
        commentRepo.save(comment);
        return "Comment has been added!!!";
    }

    public Object updateComment(Comment comment, String commenterEmail) {
        User commentOwner = userRepo.findFirstByEmail(commenterEmail);
        if (commentOwner != null) {
            Optional<Comment> existingComment = commentRepo.findCommentByCommentId(comment.getCommentId());
            if (existingComment.isPresent()) {
                Comment existingCommentEntity = existingComment.get();
                if (existingCommentEntity.getCommenter().equals(commentOwner)) {
                    existingCommentEntity.setCommentBody(comment.getCommentBody());
                    existingCommentEntity.setCommentCreationTimeStamp(LocalDateTime.now());
                    return commentRepo.save(existingCommentEntity);
                } else {
                    return "Comment does not belong to the user!!";
                }
            } else {
                return "Comment not found!!";
            }
        } else {
            return "User not found!!";
        }
    }


    public List<Comment> getAllComments() {
        return commentRepo.findAll();
    }

    public Comment findComment(Long commentId) {
        return  commentRepo.findById(commentId).orElse(null);
    }

    public void removeComment(Comment comment) {
        commentRepo.delete(comment);
    }
}