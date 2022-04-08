package com.revature.service;

import com.revature.dao.UserDAO;
import com.revature.model.User;

import javax.security.auth.login.FailedLoginException;
import java.sql.SQLException;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public UserService(UserDAO mockDao) {
        this.userDAO = mockDao;
    }

    public User login(String username, String password) throws SQLException, FailedLoginException {
        User user = this.userDAO.getUserByUserAndPass(username, password);

        if (user == null) {
            throw new FailedLoginException("Invalid user and/or pass was provided.");
        }

        return user;
    }
}
