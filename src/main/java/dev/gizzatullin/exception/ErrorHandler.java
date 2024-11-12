package dev.gizzatullin.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(final Exception e) {
        log.warn("ERROR  ", e);
        return new ErrorResponse(e.getMessage(), Arrays.toString(e.getStackTrace()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTaskNotFoundException(final TaskNotFoundException e) {
        log.warn("ERROR  ", e);
        return new ErrorResponse(e.getMessage(), Arrays.toString(e.getStackTrace()));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class,
            ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(final Exception e) {
        log.warn("ERROR  ", e);
        return new ErrorResponse(e.getMessage(), Arrays.toString(e.getStackTrace()));
    }
}
