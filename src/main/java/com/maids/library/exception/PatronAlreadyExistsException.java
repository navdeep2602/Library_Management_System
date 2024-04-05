package com.maids.library.exception;

public class PatronAlreadyExistsException extends RuntimeException{
    public PatronAlreadyExistsException(String message) {
        super(message);
    }
}