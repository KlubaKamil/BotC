package com.czachodym.BotC.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler({EntityAlreadyExists.class,
            EntityNotFoundException.class}
    )
    public ResponseEntity<?> entityAlreadyExistsHandler(Exception exception, WebRequest request){
        log.info("EBEEBE");
        return new ResponseEntity<>("Bad request", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
