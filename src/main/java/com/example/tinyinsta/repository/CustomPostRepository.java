package com.example.tinyinsta.repository;

import com.example.tinyinsta.model.Post;
import com.example.tinyinsta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CustomPostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.user.id IN :userIds ORDER BY p.createdAt DESC")
    List<Post> findFeedPosts(@Param("userIds") List<Long> userIds);
}
