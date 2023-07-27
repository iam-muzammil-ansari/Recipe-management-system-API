package com.geekster.Recipe_management_system_API.repository;

import com.geekster.Recipe_management_system_API.model.AuthenticationToken;
import com.geekster.Recipe_management_system_API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepo extends JpaRepository<AuthenticationToken,Long> {
    AuthenticationToken findFirstByToken(String token);

    AuthenticationToken findFirstByUser(User user);
}
