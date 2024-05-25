package com.peter.bookstore.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String bookName;
    private String authorName;
    private String isbn;
    private String publishedDate;

}
