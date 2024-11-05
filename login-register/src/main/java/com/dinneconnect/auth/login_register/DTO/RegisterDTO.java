package com.dinneconnect.auth.login_register.DTO;

/**
 * DTO for User Registration.
 * 
 * @author Sebastian Avendaño Rodriguez
 * @since 2024/11/04
 * @version 1.0
 */
public class RegisterDTO {

    public String name;
    public String surname;
    public String username;
    public String email;
    public String password;

    /**
     * Constructs a new RegisterDTO with the specified values.
     * 
     * @param name     the user's first name
     * @param surname  the user's surname
     * @param username the user's username
     * @param email    the user's email address
     * @param password the user's password
     */
    public RegisterDTO(String name, String surname, String username, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the user's first name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's first name.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's surname.
     * 
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the user's surname.
     * 
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the user's username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's email address.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
