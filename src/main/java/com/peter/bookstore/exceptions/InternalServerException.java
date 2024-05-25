package com.peter.bookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class InternalServerException extends RuntimeException{
    public InternalServerException(String message){
        super(message);
    }
}
