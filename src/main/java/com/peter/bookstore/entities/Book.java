package com.peter.bookstore.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book extends BaseEntity{
    private String bookName;
    private String authorName;
    private String ISBN;
    private String publishedDate;
    private boolean isAvailable;
}
