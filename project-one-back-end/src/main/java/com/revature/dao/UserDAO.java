package com.revature.dao;

import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public UserDAO() {}

    public User getUserByUserAndPass(String username, String password) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT users.id, users.username, users.user_email, user_role.user_role " +
                    "FROM users " +
                    "INNER JOIN user_role " +
                    "ON users.user_role_id = user_role.id " +
                    "WHERE users.username = ? AND users.password = ?";

            try(PreparedStatement prep = con.prepareStatement(sql)) {
                prep.setString(1, username);
                prep.setString(2, password);

                ResultSet rs = prep.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("id");
                    String user = rs.getString("username");
                    String email = rs.getString("user_email");
                    String role = rs.getString("user_role");

                    return new User(userId, user, email, role);
                }
                return null;
            }
        }
    }
}
