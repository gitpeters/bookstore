package com.peter.bookstore.repositories;

import com.peter.bookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    @Query("SELECT b FROM Book b WHERE " +
            "(:searchKey IS NULL OR LOWER(b.bookName) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
            "LOWER(b.authorName) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
            "LOWER(b.isbn) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
            "LOWER(b.publishedDate) LIKE LOWER(CONCAT('%', :searchKey, '%')))")
    List<Book> findBySearchKey(@Param("searchKey") String searchKey);

    Optional<Book> findByBookNameAndAuthorName(String bookName, String authorName);
}
