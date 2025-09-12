package com.czachodym.BotC.exception;

import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, UnexpectedTypeException.class})
    public ResponseEntity<?> badRequestExceptionsHandler(Exception exception, WebRequest request){
        log.info("{}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler({EntityAlreadyExistsException.class, UserAlreadyPresentException.class})
    public ResponseEntity<?> conflictExceptionsHandler(Exception exception, WebRequest request){
        log.info("{}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> SQLIntegrityConstraintViolationExceptionHandler(Exception exception, WebRequest request){
        log.info("{}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.PRECONDITION_REQUIRED)
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsExceptionHandler(Exception exception, WebRequest request){
        log.info("{}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .build();
    }
}
