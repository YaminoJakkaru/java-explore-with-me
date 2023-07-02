package ru.practicum.main.handler;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.exception.DataValidationException;
import ru.practicum.main.exception.NotFoundException;

import javax.validation.ValidationException;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationException handle(final ValidationException e) {
        log.warn(e.getMessage());
        return new ValidationException();
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, String> handle(final IllegalArgumentException e) {
        return Map.of("error", "Unknown state: UNSUPPORTED_STATUS");
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public PSQLException handle(final PSQLException e) {
        log.warn(e.getMessage());
        return new PSQLException(Objects.requireNonNull(e.getServerErrorMessage()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public DataValidationException handle(final DataValidationException e) {
        log.warn(e.getMessage());
        return new DataValidationException(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NotFoundException handle(final NotFoundException e) {
        log.warn(e.getMessage());
        return new NotFoundException(e.getMessage());
    }
}
