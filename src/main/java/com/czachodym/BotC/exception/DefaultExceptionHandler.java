package com.czachodym.BotC.exception;

import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> entityNotFoundHandler(Exception exception, WebRequest request){
        log.info("{}", exception.getMessage());
        return new ResponseEntity<>("Bad request", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<?> entityAlreadyExistsHandler(Exception exception, WebRequest request){
        log.info("{}", exception.getMessage());
        return new ResponseEntity<>("Conflict", new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<?> unexpectedTypeHandler(Exception exception, WebRequest request){
        log.info("{}", exception.getMessage());
        return new ResponseEntity<>("Bad request", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> SQLIntegrityConstraintViolationExceptionHandler(Exception exception, WebRequest request){
        log.info("{}", exception.getMessage());
        return new ResponseEntity<>("Precondition required", new HttpHeaders(), HttpStatus.PRECONDITION_REQUIRED);
    }
}
