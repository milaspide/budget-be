package com.milo.budget.exception;

/**
 * Base class for the exceptions generated inside the Business Logic of the project
 */
public class SystemBusinessLogicException extends RuntimeException {
    /**
     * Constructor with message and exception
     * @param message Error message
     * @param e Exception object
     */
    public SystemBusinessLogicException(String message, Exception e) {
        super(message, e);
    }

    /**
     * Constructor with error message
     * @param message Error message
     */
    public SystemBusinessLogicException(String message) {
        super(message);
    }
}
