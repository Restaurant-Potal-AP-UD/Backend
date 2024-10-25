package com.dinneconnect.auth.models;

public class LoginRequest {
    private String username;
    private String hashedPassword;

    public LoginRequest(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public Boolean authUser() {
        // This need to be changed due to the lack of BD, in other words, when the BD
        // it's connected
        // this method will be used to verify the user credentials
        if (hashedPassword.equals(hashedPassword)) {
            return true;
        }
        return false;
    }
}
