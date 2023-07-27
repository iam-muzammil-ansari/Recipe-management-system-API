package com.geekster.Recipe_management_system_API.repository;

import com.geekster.Recipe_management_system_API.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {
}
