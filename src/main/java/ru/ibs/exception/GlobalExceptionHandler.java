package ru.ibs.exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public void handleResponseStatusException(ResponseStatusException ex) {
        log.error("ResponseStatusException occurred: {}", ex.getMessage(), ex);
        throw ex;
    }
}