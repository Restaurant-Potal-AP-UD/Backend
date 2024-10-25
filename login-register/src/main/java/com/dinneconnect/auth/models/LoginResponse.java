package com.dinneconnect.auth.models;

public class LoginResponse {

    String token;
    String message;

    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

}
