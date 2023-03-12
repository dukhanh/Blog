package com.example.blog.entity.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class DateAudit implements Serializable {
    @CreatedDate
    @Column(nullable = false, updatable =false)
    private Instant createAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updateAt;
}
