package com.example.blog.repository;

import com.example.blog.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Optional<Photo> findByTitle(String title);

    @Query("SELECT p FROM Photo p WHERE p.album.id=:id")
    Page<Photo> findAllByAlbum(@Param("id") Long albumId, Pageable pageable);
}
