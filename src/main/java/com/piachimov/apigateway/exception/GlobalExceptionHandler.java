package com.piachimov.apigateway.exception;

import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
        log.error("GlobalExceptionHandler::handleNotFoundException: {}", exception.getMessage(), exception);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ErrorResponseDTO errorResponseDTO = buildErrorResponseDTO(exception, httpStatus);
        return new ResponseEntity<>(errorResponseDTO, headers, httpStatus);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleGenericException(RuntimeException exception) {
        log.error("GlobalExceptionHandler::handleGenericException: {}", exception.getMessage(), exception);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponseDTO errorResponseDTO = buildErrorResponseDTO(exception, httpStatus);
        return new ResponseEntity<>(errorResponseDTO, headers, httpStatus);
    }

    private ErrorResponseDTO buildErrorResponseDTO(Exception exception, HttpStatus httpStatus) {
        return new ErrorResponseDTO(exception.getMessage(), httpStatus.value());
    }
}
