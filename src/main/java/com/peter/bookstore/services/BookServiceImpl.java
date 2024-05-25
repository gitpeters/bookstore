package com.peter.bookstore.services;

import com.peter.bookstore.dtos.BookRequest;
import com.peter.bookstore.dtos.BookResponse;
import com.peter.bookstore.entities.Book;
import com.peter.bookstore.exceptions.InternalServerException;
import com.peter.bookstore.exceptions.ResourceNotFoundException;
import com.peter.bookstore.repositories.BookRepository;
import com.peter.bookstore.utilities.UtilityClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.peter.bookstore.utilities.SystemConstants.BAD_REQUEST;
import static com.peter.bookstore.utilities.SystemConstants.COULD_NOT_SAVE_BOOK_RECORD;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements IBookService{

    private final BookRepository bookRepository;
    @Override
    public BookResponse createBook(BookRequest request) {
        log.info("::called create book endpoint::");
        if(UtilityClass.validateRequestBody(request)) {
            Book book = UtilityClass.buildBookEntity(request);
            bookRepository.save(book);
            return UtilityClass.buildBookResponse(book);
        }
        throw new InternalServerException(COULD_NOT_SAVE_BOOK_RECORD);
    }

    @Override
    public List<BookResponse> getAllBooks(int page, int pageSize) {
        Page<Book> bookPage = bookRepository.findAll(PageRequest.of(page-1, pageSize));
        if(bookPage.hasContent()){
            return bookPage.stream().map(UtilityClass::buildBookResponse).toList();
        }
        return List.of();
    }

    @Override
    public BookResponse findBookById(String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        return bookOptional.map(UtilityClass::buildBookResponse).orElse(null);
    }

    @Override
    public BookResponse updateBookRecord(String id, BookRequest request) {
        if(Objects.isNull(request)) throw new ResourceNotFoundException(BAD_REQUEST);
        Optional<Book> bookOptional = bookRepository.findById(id);
        if(bookOptional.isPresent()){
            Book book = bookOptional.get();
            BeanUtils.copyProperties(request, book);
            return UtilityClass.buildBookResponse(book);
        }
        return null;
    }

    @Override
    public BookResponse deleteBook(String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if(bookOptional.isPresent()) {
            Book book = bookOptional.get();
            bookRepository.delete(book);
            return BookResponse.SUCCESS_RESPONSE("book record deleted");
        }
        return BookResponse.ERROR_RESPONSE("could not delete record");
    }
}
