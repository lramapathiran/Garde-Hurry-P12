package com.lavanya.api.model;

/**
 * Bean representing a AuthBody used when a user logs in.
 * @author lavanya
 */
public class AuthBody {

    private String username;
    private String password;

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

}
