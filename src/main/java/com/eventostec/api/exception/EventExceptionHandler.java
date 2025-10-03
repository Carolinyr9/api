package com.eventostec.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EventExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(EventNotFoundException.class)
    private ResponseEntity<String> eventNotFoundExceptionHandler(EventExceptionHandler exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
    }
}
