package com.geekster.Recipe_management_system_API.controller;

import com.geekster.Recipe_management_system_API.model.Comment;
import com.geekster.Recipe_management_system_API.model.User;
import com.geekster.Recipe_management_system_API.service.AuthenticationService;
import com.geekster.Recipe_management_system_API.service.CommentService;
import com.geekster.Recipe_management_system_API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Comments")
public class CommentController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @PostMapping
    public String addComment(@RequestBody Comment comment, @RequestParam String commenterEmail, @RequestParam String commenterToken) {
        if (authenticationService.authenticate(commenterEmail,commenterToken)) {
            return userService.addComment(comment,commenterEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @DeleteMapping("comment")
    public String removeInstaComment(@RequestParam Long commentId, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.removeRecipeComment(commentId,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }
}
