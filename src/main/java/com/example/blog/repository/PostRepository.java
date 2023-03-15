package com.example.blog.repository;

import com.example.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByTitle(String title);

    Page<Post> findByTitleContains( String title, Pageable pageable);

    Page<Post> findAll(Pageable pageable);
}
