package com.revature.controller;

import com.revature.dto.LoginDTO;
import com.revature.main.Driver;
import com.revature.model.User;
import com.revature.service.JWTService;
import com.revature.service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationController implements Controller{

    private UserService userService;
    private JWTService jwtService;

    public static Logger logger = LoggerFactory.getLogger(Driver.class);

    public AuthenticationController() {
        this.userService = new UserService();
        this.jwtService = JWTService.getInstance();
    }

    private final Handler login = ctx -> {

        // create a user/pass object
        LoginDTO loginInfo = ctx.bodyAsClass(LoginDTO.class);
        // user Service returns a user with (user/pass)
        // create user object & send this user object to jwtService
        User user = userService.login(loginInfo.getUsername(), loginInfo.getPassword());

        logger.info("\nLogin endpoint has been successfully invoked." +
                "\nPath: " + ctx.path() +
                "\nLogin DTO val -> USER : " + loginInfo.getUsername() + " Pass : " + loginInfo.getPassword() +
                "\nLogin user val : " + user +
                "\nIP Address : " + ctx.ip() +
                "\nUser Agent: " + ctx.userAgent() + "\n");

        //jwt service will build a token for this user using all of their credentials
        String jwt = this.jwtService.createJWT(user);


        ctx.header("Access-Control-Expose-Headers", "*");
        ctx.header("Token", jwt);
        ctx.json(user);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.post("/login", login);
    }
}
