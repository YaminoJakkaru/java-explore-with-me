package ru.practicum.main.handler;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.exception.DataValidationException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.stats.dto.error.ApiError;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handle(final MethodArgumentNotValidException e) {
        log.warn(Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
        return new ApiError()
                .setMassage(e.getMessage())
                .setReason("Incorrectly made request.")
                .setStatus(HttpStatus.BAD_REQUEST.toString())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handle(final HttpMessageNotReadableException e) {
        log.warn(e.getMessage());
        return new ApiError()
                .setMassage(e.getMessage())
                .setReason("Incorrectly made request.")
                .setStatus(HttpStatus.BAD_REQUEST.toString())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handle(final MissingServletRequestParameterException e) {
        log.warn(e.getMessage());
        return new ApiError()
                .setMassage(e.getMessage())
                .setReason("Incorrectly made request.")
                .setStatus(HttpStatus.BAD_REQUEST.toString())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handle(final ValidationException e) {
        log.warn(e.getMessage());
        return new ApiError()
                .setMassage(e.getMessage())
                .setReason("Incorrectly made request.")
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiError handle(final IllegalArgumentException e) {
        log.warn(e.getMessage());
        return new ApiError()
                .setMassage(e.getMessage())
                .setReason("Incorrectly made request.")
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .setTimestamp(LocalDateTime.now());
    }


    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handle(final PSQLException e) {
        log.warn(e.getMessage());
        return new ApiError()
                .setMassage(e.getMessage())
                .setReason("Integrity constraint has been violated.")
                .setStatus(HttpStatus.CONFLICT.toString())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(DataValidationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handle(final DataValidationException e) {
        log.warn(e.getMessage());
        return new ApiError()
                .setMassage(e.getMessage())
                .setReason("For the requested operation the conditions are not met.")
                .setStatus(HttpStatus.CONFLICT.toString())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handle(final NotFoundException e) {
        log.warn(e.getMessage());
        return new ApiError()
                .setMassage(e.getMessage())
                .setReason("The required object was not found.")
                .setStatus(HttpStatus.NOT_FOUND.toString())
                .setTimestamp(LocalDateTime.now());
    }
}
