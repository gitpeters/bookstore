package com.peter.bookstore.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {
    private String id;
    private String bookName;
    private String authorName;
    private String ISBN;
    private String publishedDate;
    private boolean isAvailable;
}
