package com.peter.bookstore;

import com.peter.bookstore.dtos.BookRequest;
import com.peter.bookstore.dtos.BookResponse;
import com.peter.bookstore.entities.Book;
import com.peter.bookstore.exceptions.BadRequestException;
import com.peter.bookstore.exceptions.ResourceNotFoundException;
import com.peter.bookstore.repositories.BookRepository;
import com.peter.bookstore.services.BookServiceImpl;
import com.peter.bookstore.utilities.UtilityClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book mockBook;

    @BeforeEach
    void setUp() {
        mockBook = new Book();
        mockBook.setId("1");
        mockBook.setBookName("Test Book Name");
        mockBook.setAuthorName("Test Author Name");
        mockBook.setIsbn("ISBN98434");
        mockBook.setPublishedDate("2024-05-25");
    }

    @Test
    void testCreateBook() {
        BookRequest request = new BookRequest("Test Book Name", "Test Author Name", "ISBN98434", "2024-05-25");
        when(bookRepository.findByBookNameAndAuthorName(anyString(), anyString())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(mockBook);

        BookResponse response = bookService.createBook(request);

        assertNotNull(response);
        assertEquals(mockBook.getId(), response.getId());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testCreateBook_AlreadyExists() {
        BookRequest request = new BookRequest("Test Book Name", "Test Author Name", "ISBN98434", "2024-05-25");
        when(bookRepository.findByBookNameAndAuthorName(anyString(), anyString())).thenReturn(Optional.of(mockBook));

        assertThrows(BadRequestException.class, () -> {
            bookService.createBook(request);
        });

        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testSearchForBook() {
        when(bookRepository.findBySearchKey(anyString())).thenReturn(List.of(mockBook));

        List<BookResponse> response = bookService.searchForBook("Test");

        assertNotNull(response);
        assertFalse(response.isEmpty());
        verify(bookRepository, times(1)).findBySearchKey(anyString());
    }

    @Test
    void testFindBookById() {
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(mockBook));

        BookResponse response = bookService.findBookById("1");

        assertNotNull(response);
        assertEquals(mockBook.getId(), response.getId());
        verify(bookRepository, times(1)).findById(anyString());
    }

    @Test
    void testUpdateBookRecord() {
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(mockBook));
        when(bookRepository.save(any(Book.class))).thenReturn(mockBook);

        BookRequest request = new BookRequest("Updated Book Name", "Updated Author Name", "ISBN98434", "2024-05-25");
        BookResponse response = bookService.updateBookRecord("1", request);

        assertNotNull(response);
        assertEquals("Updated Book Name", response.getBookName());
        verify(bookRepository, times(1)).findById(anyString());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.deleteBook("1");
        });

        verify(bookRepository, times(1)).findById(anyString());
        verify(bookRepository, never()).delete(any(Book.class));
    }

    @Test
    void testDeleteBook() {
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(mockBook));

        bookService.deleteBook("1");

        verify(bookRepository, times(1)).findById(anyString());
        verify(bookRepository, times(1)).delete(any(Book.class));
    }
}
