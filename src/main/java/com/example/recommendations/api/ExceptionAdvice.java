package com.example.recommendations.api;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetails> handleBadRequest(IllegalArgumentException ex) {
        ProblemDetails p = new ProblemDetails("https://example.org/probs/invalid-argument", "Invalid argument", 400, ex.getMessage(), Instant.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(p);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetails> handleValidation(MethodArgumentNotValidException ex) {
        ProblemDetails p = new ProblemDetails("https://example.org/probs/validation", "Validation failed", 400, ex.getMessage(), Instant.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(p);
    }

    @ExceptionHandler(java.util.NoSuchElementException.class)
    public ResponseEntity<ProblemDetails> handleNotFound(java.util.NoSuchElementException ex) {
        ProblemDetails p = new ProblemDetails("https://example.org/probs/not-found", "Not found", 404, ex.getMessage(), Instant.now().toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(p);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetails> handleGeneric(Exception ex) {
        ProblemDetails p = new ProblemDetails("https://example.org/probs/internal", "Internal error", 500, ex.getMessage(), Instant.now().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(p);
    }
}
