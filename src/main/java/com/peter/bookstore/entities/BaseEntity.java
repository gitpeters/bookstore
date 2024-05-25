package com.peter.bookstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@Data
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @CreatedDate
    @JsonIgnore
    private LocalDateTime createdAt = LocalDateTime.now();
    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime lastModifiedAt;

    @PreUpdate
    protected void onUpdate(){
        lastModifiedAt = LocalDateTime.now();
    }
}
