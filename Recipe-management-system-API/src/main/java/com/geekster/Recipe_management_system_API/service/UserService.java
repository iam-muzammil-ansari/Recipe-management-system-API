package com.geekster.Recipe_management_system_API.service;

import com.geekster.Recipe_management_system_API.model.AuthenticationToken;
import com.geekster.Recipe_management_system_API.model.Comment;
import com.geekster.Recipe_management_system_API.model.DataToObject.SignInInput;
import com.geekster.Recipe_management_system_API.model.DataToObject.SignUpOutput;
import com.geekster.Recipe_management_system_API.model.User;
import com.geekster.Recipe_management_system_API.repository.AuthRepo;
import com.geekster.Recipe_management_system_API.repository.UserRepo;
import com.geekster.Recipe_management_system_API.service.emailUtility.EmailHandler;
import com.geekster.Recipe_management_system_API.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthRepo authRepo;

    @Autowired
    RecipeService recipeService;

    @Autowired
    CommentService commentService;

    public SignUpOutput SignUpUser(User user) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getEmail();

        if(newEmail == null)
        {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //check if this patient email already exists ??
        User existingUser = userRepo.findFirstByEmail(newEmail);

        if(existingUser != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getPassword());

            //saveAppointment the patient with the new encrypted password

            user.setPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        }
        catch(Exception e)
        {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }

    public String signInUser(SignInInput signInInput) {
        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this patient email already exists ??
        User existingUser = userRepo.findFirstByEmail(signInEmail);

        if(existingUser == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingUser.getPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingUser);
                authRepo.save(authToken);

                EmailHandler.sendEmail(signInEmail,"email testing",authToken.getToken());
                return "Token sent to your email";
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }
    }

    public String signOutUser(String email) {
        User user = userRepo.findFirstByEmail(email);
        if (user != null) {
            authRepo.delete(authRepo.findFirstByUser(user));
            return "User Signed out Successfully";
        } else {
            return "User not found!";
        }
    }

    public String addComment(Comment comment, String commenterEmail) {
        boolean recipeValid = recipeService.validateRecipe(comment.getRecipe());
        if (recipeValid) {
            User commenter = userRepo.findFirstByEmail(commenterEmail);
            comment.setCommenter(commenter);
            return commentService.addComment(comment);
        }else {
            return "Cannot comment on Invalid Post!!!";
        }
    }

    public String removeRecipeComment(Long commentId, String email) {
        Comment comment = commentService.findComment(commentId);
        if(comment!=null) {
            if(authorizeCommentRemover(email,comment)) {
                commentService.removeComment(comment);
                return  "comment deleted successfully";
            }else {
                return "Unauthorized delete detected...Not allowed!!!!";
            }
        }else
        {
            return "Invalid Comment";
        }
    }

    private boolean authorizeCommentRemover(String email, Comment comment) {
        String commentOwnerEmail = comment.getCommenter().getEmail();
        String recipeOwnerEmail = comment.getRecipe().getOwner().getEmail();
        return recipeOwnerEmail.equals(email) || commentOwnerEmail.equals(email);
    }


}
