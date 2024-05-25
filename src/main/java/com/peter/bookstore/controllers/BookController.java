package com.peter.bookstore.controllers;

import com.peter.bookstore.dtos.BookRequest;
import com.peter.bookstore.dtos.BookResponse;

import com.peter.bookstore.services.IBookService;
import com.peter.bookstore.utilities.SystemConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.peter.bookstore.utilities.UtilityClass.validateRequestBody;


@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final IBookService bookService;

    @PostMapping("/create")
    public ResponseEntity<BookResponse> createBookRecord(@RequestBody BookRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(request));
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> fetchAllBooks(@RequestParam(name = "page", defaultValue = "1") int page,
                                                            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        return ResponseEntity.ok(bookService.getAllBooks(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBookById(@PathVariable String id){
        if(Objects.nonNull(bookService.findBookById(id))){
            return ResponseEntity.ok(bookService.findBookById(id));
        }
        return ResponseEntity.badRequest().body(SystemConstants.NOT_FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchForBook(@RequestParam("searchKey")String searchKey){
        return ResponseEntity.ok(bookService.searchForBook(searchKey));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBookRecord(@PathVariable String id, @RequestBody BookRequest request){
        return ResponseEntity.ok(bookService.updateBookRecord(id, request));
    }
    @PatchMapping("/{id}/update-book-status")
    public ResponseEntity<?> updateBookStatus(@PathVariable String id){
        return ResponseEntity.ok(bookService.updateBookStatus(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable String id){
        return ResponseEntity.ok(bookService.deleteBook(id));
    }
}
