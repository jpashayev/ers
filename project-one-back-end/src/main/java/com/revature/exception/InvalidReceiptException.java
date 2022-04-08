package com.revature.exception;

public class InvalidReceiptException extends Exception{
    public InvalidReceiptException() {
    }

    public InvalidReceiptException(String message) {
        super(message);
    }

    public InvalidReceiptException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidReceiptException(Throwable cause) {
        super(cause);
    }

    public InvalidReceiptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
