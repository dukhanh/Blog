package com.example.blog.entity;

import com.example.blog.entity.audit.UserDateAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Photo extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String url;
    @NotBlank
    private String thumbnailUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="album_id")
    private Album album;

}
