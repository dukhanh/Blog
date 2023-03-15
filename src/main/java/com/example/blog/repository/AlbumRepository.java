package com.example.blog.repository;

import com.example.blog.entity.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByTitle(String title);
    Page<Album> findByTitleContains(String title, Pageable pageable);
    Page<Album> findAll(Pageable pageable);
}
