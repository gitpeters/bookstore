package com.peter.bookstore.utilities;

import com.peter.bookstore.dtos.BookRequest;
import com.peter.bookstore.dtos.BookResponse;
import com.peter.bookstore.entities.Book;
import com.peter.bookstore.exceptions.BadRequestException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class UtilityClass {

    public static Book buildBookEntity(BookRequest request){
        Book book = new Book();
        book.setBookName(request.getBookName());
        book.setIsbn(request.getIsbn());
        book.setAuthorName(request.getAuthorName());
        book.setPublishedDate(request.getPublishedDate());
        return book;
    }

    public static BookResponse buildBookResponse(Book book){
        return BookResponse.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .publishedDate(book.getPublishedDate())
                .isAvailable(book.isAvailable())
                .build();
    }

    public static void validateRequestBody(BookRequest request) {
        if (request.getAuthorName() == null || request.getAuthorName().trim().isEmpty()) {
            throw new BadRequestException("authorName is required");
        }
        if (request.getBookName() == null || request.getBookName().trim().isEmpty()) {
            throw new BadRequestException("bookName is required");
        }
        if (request.getIsbn() == null || request.getIsbn().trim().isEmpty()) {
            throw new BadRequestException("isbn is required");
        }
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
}

}
