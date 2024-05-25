package com.peter.bookstore.utilities;

import com.peter.bookstore.dtos.BookRequest;
import com.peter.bookstore.dtos.BookResponse;
import com.peter.bookstore.entities.Book;
import com.peter.bookstore.exceptions.ResourceNotFoundException;

public class UtilityClass {

    public static Book buildBookEntity(BookRequest request){
        Book book = new Book();
        book.setBookName(request.getBookName());
        book.setISBN(request.getISBN());
        book.setAuthorName(request.getAuthorName());
        book.setPublishedDate(request.getPublishedDate());
        return book;
    }

    public static BookResponse buildBookResponse(Book book){
        return BookResponse.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .authorName(book.getAuthorName())
                .ISBN(book.getISBN())
                .publishedDate(book.getPublishedDate())
                .isAvailable(book.isAvailable())
                .build();
    }

    public static boolean validateRequestBody(BookRequest request){
        if(request.getBookName()==null) throw new ResourceNotFoundException("bookName is required");
        if(request.getAuthorName()==null) throw new ResourceNotFoundException("authorName is required");
        if(request.getISBN()==null) throw new ResourceNotFoundException("ISBN is required");
        return true;
    }
}
