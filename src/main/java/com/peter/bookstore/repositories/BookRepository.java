package com.peter.bookstore.repositories;

import com.peter.bookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
