package com.revature.main;

import com.revature.controller.AuthenticationController;
import com.revature.controller.Controller;
import com.revature.controller.ExceptionController;
import com.revature.controller.ReimbursementController;
import io.javalin.Javalin;

public class Driver {

    public static void main(String[] args) {

        // create a new javalin app
        Javalin app = Javalin.create((config) -> {
            config.enableCorsForOrigin("http://127.0.0.1:5500/");
        });

        // map the app with its controllers
        map(app, new AuthenticationController(), new ExceptionController(), new ReimbursementController());

        // start the app
        app.start(8080);

    }

    public static void map(Javalin app, Controller...controllers) {
        for (Controller c: controllers) {
            c.mapEndpoints(app);
        }
    }

}
