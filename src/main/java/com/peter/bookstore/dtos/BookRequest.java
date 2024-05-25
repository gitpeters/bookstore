package com.peter.bookstore.dtos;

import lombok.Data;

@Data
public class BookRequest {
    private String bookName;
    private String authorName;
    private String ISBN;
    private String publishedDate;
}
