package com.revature.model;

import java.util.Objects;

public class User {

    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private int userRoleId;

    // possibly make use role a class?
    private String userRole;

    public User() {}

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public User(int id, String username, String email, String userRole) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.userRole = userRole;
    }

    public User(int id, String username, String password, String firstName, String lastName, String email, int userRoleId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRoleId = userRoleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user1 = (User) o;
        return id == user1.id && userRoleId == user1.userRoleId && Objects.equals(username, user1.username) && Objects.equals(password, user1.password) && Objects.equals(firstName, user1.firstName) && Objects.equals(lastName, user1.lastName) && Objects.equals(email, user1.email) && Objects.equals(userRole, user1.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstName, lastName, email, userRoleId, userRole);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user='" + username + '\'' +
                ", pass='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", userRoleId=" + userRoleId +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
