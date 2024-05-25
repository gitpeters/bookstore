package com.peter.bookstore.services;

import com.peter.bookstore.dtos.BookRequest;
import com.peter.bookstore.dtos.BookResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IBookService {
    BookResponse createBook(BookRequest request);

    List<BookResponse> getAllBooks(int page, int pageSize);

    BookResponse findBookById(String id);

    BookResponse updateBookRecord(String id, BookRequest request);

    BookResponse deleteBook(String id);
}
