package com.vivekanandpv.springbootapijpafileiovalidation.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String message) {
        super(message);
    }
}

