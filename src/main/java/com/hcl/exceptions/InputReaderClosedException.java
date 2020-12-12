package com.hcl.exceptions;

public class InputReaderClosedException extends Exception {
    private static final long serialVersionUID = 1L;

    public InputReaderClosedException(String message) {
        super(message);
    }

    public InputReaderClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
