package com.youcode.citronix.web.errors;

import com.youcode.citronix.exception.AlreadyExistException;
import com.youcode.citronix.exception.NullOrBlankArgException;
import com.youcode.citronix.exception.ResourceNotFoundException;
import com.youcode.citronix.exception.NotValidConstraintException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> handleValidationExceptions(MethodArgumentNotValidException exception){
        Map<String , String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField() , error.getDefaultMessage()));
        return new ResponseEntity<>(errors , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotValidConstraintException.class)
    public ResponseEntity<Map<String, String>> handleNotValidConstraintException(NotValidConstraintException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyExistException(AlreadyExistException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NullOrBlankArgException.class)
    public ResponseEntity<Map<String, String>> handleNullOrBlankArgException(NullOrBlankArgException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
