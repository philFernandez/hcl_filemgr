package com.hcl.exceptions;


public class InvalidMenuChoiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidMenuChoiceException(String msg, Throwable err) {
        super(msg, err);
    }

    public InvalidMenuChoiceException(String msg) {
        super(msg);
    }

}
