package com.dinneconnect.auth.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a User in the system with personal details and associated
 * restaurants.
 * This class holds user information such as name, surname, username, email,
 * phone number,
 * and password. It also manages the user's associated restaurants and provides
 * methods
 * to update the user's password and retrieve restaurant information.
 * 
 * @author Sebastian Avendaño Rodriguez
 * @since 20/10/2024
 * @version 1.0
 * 
 */

public class User {
    private String name;
    private String surname;
    public String username;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDateTime creationDate;
    private Boolean activeReservation = false;
    private Boolean verified = false;
    private Boolean isActive = false;

    /**
     * Constructs a new User with the specified details.
     * 
     * @param name        String - The name of the user.
     * @param surname     String - The surname of the user.
     * @param username    String - The username of the user.
     * @param email       String - The email address of the user.
     * @param phoneNumber String - The phone number of the user.
     * @param password    String - The password of the user.
     */
    public User(String name, String surname, String username, String email, String phoneNumber,
            String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.creationDate = LocalDateTime.now();
    }

    /**
     * Sets a new password for the user after verifying the current password.
     * 
     * @param rawPassword The current password of the user.
     * @param newPassword The new password to be set.
     */
    public void setNewPassword(String rawPassword, String newPassword) {
        if (!this.password.equals(rawPassword)) {
            // This should be changed, this should launch a status_code 400, due to the
            // incorrect data
            System.out.println("The password is incorrect");
        } else {
            this.password = newPassword;
            // This should raise status_code 200 OK
            System.out.println("The password has been changed");
        }
    }

}
