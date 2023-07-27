package com.geekster.Recipe_management_system_API.service;

import com.geekster.Recipe_management_system_API.model.AuthenticationToken;
import com.geekster.Recipe_management_system_API.repository.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    AuthRepo authRepo;

    public boolean authenticate(String email, String token) {
        AuthenticationToken authenticationToken = authRepo.findFirstByToken(token);

        if(authenticationToken == null) {
            return  false;
        }
        String tokenConnectedEmail = authenticationToken.getUser().getEmail();

        return tokenConnectedEmail.equals(email);
    }
}
