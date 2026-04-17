package com.tournament.api.exception;

/**
 * Custom runtime exception thrown when a species registration fails because the name is already taken.
 * <p>
 * This exception is caught by the {@link GlobalExceptionHandler} to provide a structured error response to the client.
 * </p>
 *
 * @author Anthony Ortega
 * @version 1.0
 */
public class SpeciesAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new exception with a specific error message.
     *
     * @param message Detailed explanation of the conflict (e.g., the name of the duplicated species).
     */
    public SpeciesAlreadyExistsException(String message) {
        super(message);
    }
}