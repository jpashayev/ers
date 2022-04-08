package com.revature.controller;

import com.revature.exception.IllegalStatusException;
import com.revature.exception.InvalidReceiptException;
import com.revature.exception.ReceiptNotFoundException;
import com.revature.main.Driver;
import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.FailedLoginException;

public class ExceptionController implements Controller{

    public static Logger logger = LoggerFactory.getLogger(Driver.class);

    // create a handler for failed logins
    // has ctx and exception &&  output status code / json / logging
    private final ExceptionHandler<FailedLoginException> failedLogin = (exception, ctx) -> {

        logger.info("\nLogin endpoint has caught a Failed Login Exception." +
                "\nPath: " + ctx.path() +
                "\nBody : " + ctx.body() +
                "\nIP Address : " + ctx.ip() +
                "\nUser Agent: " + ctx.userAgent() + "\n");

        ctx.status(400);
        ctx.json(exception.getMessage());
    };

    private final ExceptionHandler<InvalidReceiptException> invalidReceipt = (exception, ctx) -> {

        logger.info("\nLogin endpoint has caught a Failed Receipt Exception." +
                "\nPath: " + ctx.path() +
                "\nBody : " + ctx.body() +
                "\nIP Address : " + ctx.ip() +
                "\nUser Agent: " + ctx.userAgent() + "\n");

        ctx.status(400);
        ctx.json(exception.getMessage());
    };

    private final ExceptionHandler<IllegalStatusException> invalidStatus = (exception, ctx) -> {

        logger.info("\nLogin endpoint has caught a Failed Status Exception." +
                "\nPath: " + ctx.path() +
                "\nBody : " + ctx.body() +
                "\nIP Address : " + ctx.ip() +
                "\nUser Agent: " + ctx.userAgent() + "\n");

        ctx.status(400);
        ctx.json(exception.getMessage());
    };

    private final ExceptionHandler<IllegalArgumentException> illegalArgument = (exception, ctx) -> {

        logger.info("\nLogin endpoint has caught a Failed Status Exception." +
                "\nPath: " + ctx.path() +
                "\nBody : " + ctx.body() +
                "\nIP Address : " + ctx.ip() +
                "\nUser Agent: " + ctx.userAgent() + "\n");

        ctx.status(400);
        ctx.json(exception.getMessage());
    };

    private final ExceptionHandler<ReceiptNotFoundException> receiptNotFound = (exception, ctx) -> {
        ctx.status(404);
        ctx.json(exception.getMessage());
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.exception(FailedLoginException.class, failedLogin);
        app.exception(InvalidReceiptException.class, invalidReceipt);
        app.exception(IllegalStatusException.class, invalidStatus);
        app.exception(IllegalArgumentException.class, illegalArgument);
        app.exception(ReceiptNotFoundException.class, receiptNotFound);
    }
}
