package com.peter.bookstore.dtos;

import com.peter.bookstore.utilities.SystemConstants;
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
    private String status;
    private String message;

    public BookResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static BookResponse SUCCESS_RESPONSE(String message){
        return BookResponse.builder()
                .status(SystemConstants.SUCCESS)
                .message(message)
                .build();
    }

    public static BookResponse ERROR_RESPONSE(String message){
        return BookResponse.builder()
                .status(SystemConstants.ERROR)
                .message(message)
                .build();
    }
}
