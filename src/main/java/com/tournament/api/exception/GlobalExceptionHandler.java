package com.tournament.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the API.
 * <p>
 * This class intercepts specific exceptions thrown by the service layer or during request validation, providing a consistent and structured JSON response format for the client.
 * </p>
 *
 * @author Anthony Ortega
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles conflicts when a user attempts to register a species that already exists.
     *
     * @param speciesAlreadyExistsException The {@link SpeciesAlreadyExistsException} thrown by the service.
     * @return A {@link ResponseEntity} containing a map with error details and HTTP 400 status.
     */
    @ExceptionHandler(SpeciesAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyExists(SpeciesAlreadyExistsException speciesAlreadyExistsException) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Registration Conflict");
        error.put("message", speciesAlreadyExistsException.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Catches and formats validation errors triggered by {@code @Valid} annotations in DTOs.
     * <p>
     * This method iterates through all field errors and returns them as a map where the key is the field name and the value is the validation message.
     * </p>
     *
     * @param methodArgumentNotValidException The exception thrown when method arguments fail validation.
     * @return A {@link ResponseEntity} with the map of validation errors and HTTP 400 status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
