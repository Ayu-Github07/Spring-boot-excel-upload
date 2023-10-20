package com.example.springbootwithreact.config;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.springbootwithreact.dto.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Set<String> errors = new HashSet<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage().replace("{}", fieldName);
            errors.add(errorMessage);
        });

        List<String> errorList = errors.stream().collect(Collectors.toList());

        Response response = new Response(null, errorList, LocalDateTime.now());

        return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
    }

}