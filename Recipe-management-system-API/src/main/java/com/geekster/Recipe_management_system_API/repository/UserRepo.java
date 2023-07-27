package com.geekster.Recipe_management_system_API.repository;

import com.geekster.Recipe_management_system_API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findFirstByEmail(String newEmail);

    User findUserNameByEmail(String commenterEmail);
}
