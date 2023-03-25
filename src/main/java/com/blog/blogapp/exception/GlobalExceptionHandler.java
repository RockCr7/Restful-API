package com.blog.blogapp.exception;

import com.blog.blogapp.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice  //any exception happens spring boot will pass it to this class
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //handle specific exception
    //resource-not-found will create object and will go to controller-advice and to this method

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest ){

        ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleAllException(Exception exception,
                                                           WebRequest webRequest ){

        ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
