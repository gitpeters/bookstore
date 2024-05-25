package com.peter.bookstore.services;

import com.peter.bookstore.dtos.BookRequest;
import com.peter.bookstore.dtos.BookResponse;
import com.peter.bookstore.entities.Book;
import com.peter.bookstore.exceptions.BadRequestException;
import com.peter.bookstore.exceptions.InternalServerException;
import com.peter.bookstore.exceptions.ResourceNotFoundException;
import com.peter.bookstore.repositories.BookRepository;
import com.peter.bookstore.utilities.UtilityClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.peter.bookstore.utilities.SystemConstants.*;
import static com.peter.bookstore.utilities.UtilityClass.getNullPropertyNames;
import static com.peter.bookstore.utilities.UtilityClass.validateRequestBody;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements IBookService{

    private final BookRepository bookRepository;
    @Override
    public BookResponse createBook(BookRequest request) {
        log.info("::called create book endpoint::");
        validateRequestBody(request);
        Optional<Book> bookOptional = bookRepository.findByBookNameAndAuthorName(request.getBookName(), request.getAuthorName());
        if(bookOptional.isPresent()) throw new BadRequestException(BOOK_ALREADY_EXIST);
        try{
            Book book = UtilityClass.buildBookEntity(request);
            bookRepository.save(book);
            return UtilityClass.buildBookResponse(book);
        }catch (Exception e){
            throw new InternalServerException(COULD_NOT_SAVE_BOOK_RECORD);
        }
    }

    @Override
    public List<BookResponse> getAllBooks(int page, int pageSize) {
        int pageNo = page - 1; // PageRequest pages are 0-based
        if(pageNo < 0){
            pageNo += 1;
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").ascending());
        log.info("::retrieve all books endpoint::");
        Page<Book> bookPage = bookRepository.findAll(pageable);
        if(bookPage.hasContent()){
            return bookPage.stream().map(UtilityClass::buildBookResponse).toList();
        }
        return List.of();
    }

    @Override
    public BookResponse findBookById(String id) {
        Book book = getBookById(id);
        return UtilityClass.buildBookResponse(book);
    }

    @Override
    public BookResponse updateBookRecord(String id, BookRequest request) {
        log.info("::edit book endpoint::");
        if(Objects.isNull(request)) throw new ResourceNotFoundException(BAD_REQUEST);
        Book book = getBookById(id);
        BeanUtils.copyProperties(request, book, getNullPropertyNames(request));
        Book editedBook = bookRepository.save(book);
        return UtilityClass.buildBookResponse(editedBook);
    }

    @Override
    public BookResponse updateBookStatus(String id) {
        Book book = getBookById(id);
        book.setAvailable(!book.isAvailable());
        bookRepository.save(book);
        return UtilityClass.buildBookResponse(book);
    }

    @Override
    public BookResponse deleteBook(String id) {
        log.info("::delete book endpoint::");
        Book book = getBookById(id);
        bookRepository.delete(book);
        return BookResponse.SUCCESS_RESPONSE("book record deleted");
    }

    @Override
    public List<BookResponse> searchForBook(String searchKey) {
        log.info("::search book endpoint::");
        List<Book> books = bookRepository.findBySearchKey(searchKey);
        if(!books.isEmpty()){
            return books.stream().map(UtilityClass::buildBookResponse).toList();
        }
        return List.of();
    }

    // helper method to retrieve book by id
    private Book getBookById(String bookId){
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isPresent()){
            return bookOptional.get();
        }
        throw new ResourceNotFoundException(NOT_FOUND);
    }
}
