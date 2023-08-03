package ru.practicum.stats.hendler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

}
