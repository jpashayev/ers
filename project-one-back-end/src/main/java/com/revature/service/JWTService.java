package com.revature.service;

import com.revature.main.Driver;
import com.revature.model.User;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;

public class JWTService {

    private static JWTService instance;
    private final Key key;
    public Logger log = LoggerFactory.getLogger(Driver.class);

    private JWTService() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS384);
    }

    public static JWTService getInstance() {
        if (JWTService.instance == null) {
            JWTService.instance = new JWTService();
        }
        return JWTService.instance;
    }

    public String createJWT(User user) {

        // claims = column values
        String jwt = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("user_id", user.getId())
                .claim("username", user.getUsername())
                .claim("user_role", user.getUserRole())
                .signWith(key)
                .compact();
        log.info("\nA token has been built, returning said token. [JWT Service Layer]\n");
        return jwt;
    }

    public Jws<Claims> parseJWT(String jwt) {
        try {
            log.info("\nAttempting to parse given JWT token\n");
            Jws<Claims> token = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            log.info("\nParsing Successful! Returning token\n");
            return token;
        } catch (JwtException e) {
            log.info("\nInvalid JWT provided by client\n");
            throw new UnauthorizedResponse("JWT that was provided by the client was invalid");
        }
    }
}
