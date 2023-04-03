package com.globits.da.constants;

public enum ErrorCode {
    SUCCESS("Request was successful"),
    UNAUTHORIZED("Invalid credentials"),
    FORBIDDEN("You don't have permission to access this resource"),
    NOT_FOUND("The requested resource was not found"),
    INTERNAL_SERVER_ERROR("An internal server error occurred");


    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}