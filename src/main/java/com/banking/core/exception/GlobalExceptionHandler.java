package com.banking.core.exception;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    //404 Not Found
    @ExceptionHandler(AccountNotFoundException.class)
    public ProblemDetail handleAccountNotFound(AccountNotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Account Not Found");
        problemDetail.setType(URI.create("http://api.banking.com/errors/account-not-found"));
        problemDetail.setProperty("timestamp",Instant.now());
        return problemDetail;
    }

    //400 Bad Request
    @ExceptionHandler(InsufficientBalanceException.class)
    public ProblemDetail handleInsufficientException(InsufficientBalanceException ex){
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
            problemDetail.setTitle("Insufficient Balance");
            problemDetail.setType(URI.create("http://api.banking.com/errors/insufficient-balance"));
            problemDetail.setProperty("timestamp", Instant.now());
            return problemDetail;
        }
    
        //Dto validation Exception
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex){
            String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ":" + error.getDefaultMessage())
                .collect(Collectors.joining(","));

            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errors);
                problemDetail.setTitle("Validation Failed!");
                problemDetail.setType(URI.create("http://api.banking.com/errors/validation-failed"));
                problemDetail.setProperty("timestamp",Instant.now());
                return problemDetail;
        }
}
