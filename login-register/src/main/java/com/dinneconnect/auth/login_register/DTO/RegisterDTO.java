package com.dinneconnect.auth.login_register.DTO;

public class RegisterDTO {

    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;

    public RegisterDTO(String name, String surname, String username, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
