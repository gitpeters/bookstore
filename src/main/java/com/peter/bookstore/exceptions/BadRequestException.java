package com.peter.bookstore.exceptions;


public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
