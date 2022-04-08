package com.revature.exception;

public class IllegalStatusException extends Exception {

    public IllegalStatusException() {
    }

    public IllegalStatusException(String s) {
        super(s);
    }

    public IllegalStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalStatusException(Throwable cause) {
        super(cause);
    }

    public IllegalStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
