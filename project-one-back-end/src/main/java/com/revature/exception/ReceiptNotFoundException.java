package com.revature.exception;

public class ReceiptNotFoundException extends Exception {
    public ReceiptNotFoundException(String there_was_no_receipt) {

    }

    public ReceiptNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceiptNotFoundException(Throwable cause) {
        super(cause);
    }

    public ReceiptNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ReceiptNotFoundException() {
    }
}
