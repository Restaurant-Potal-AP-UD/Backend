package com.dinneconnect.auth.login_register.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.dinneconnect.auth.login_register.DTO.RegisterDTO;

/**
 * This class represents a User entity with various attributes related to user
 * information.
 * It is mapped to the "User" table in the database.
 * 
 * <p>
 * Each user has an ID, name, surname, username, email, password, creation date,
 * reservation status,
 * verification status, activity status, and an associated restaurant ID.
 * </p>
 * 
 * <p>
 * The {@code User} class is annotated with {@code @Entity} and {@code @Table}
 * to indicate that
 * it is a JPA entity and to specify the table name in the database.
 * </p>
 * 
 * <p>
 * The class contains standard getters and setters for each attribute.
 * </p>
 * 
 * @author
 */

public class User {

    private Long code;

    /**
     * Name of the user.
     */
    private String name;

    /**
     * Surname of the user.
     */

    private String surname;

    /**
     * Unique username of the user.
     */

    private String username;

    /**
     * Unique email of the user.
     */

    private String email;

    /**
     * Hashed password of the user.
     */

    private String password;

    /**
     * Creation date of the user account.
     */

    private String creationDate;

    /**
     * Reservation status of the user.
     */

    private Boolean reservation;

    /**
     * Verification status of the user.
     */
    private Boolean verified;

    /**
     * Activity status of the user.
     */
    private Boolean active;

    public User() {
    }

    /**
     * 
     * @param user
     */
    public User(RegisterDTO user) {
        this.code = ThreadLocalRandom.current().nextLong(0, 1000000000000L);
        this.name = user.name;
        this.surname = user.surname;
        this.username = user.username;
        this.email = user.email;
        this.password = user.password;
        this.creationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.reservation = false;
        this.verified = false;
        this.active = false;
    }

    public Boolean verifyPassword(String password) {
        return (this.password.equals(password));
    }

    // ============================ Getters and Setters
    // ============================================== //

    /**
     * Gets the name of the user.
     * 
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * 
     * @param name the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the user.
     * 
     * @return the surname of the user
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the user.
     * 
     * @param surname the surname of the user
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the unique username of the user.
     * 
     * @return the unique username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the unique username of the user.
     * 
     * @param username the unique username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the unique email of the user.
     * 
     * @return the unique email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the unique email of the user.
     * 
     * @param email the unique email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the creation date of the user account.
     * 
     * @return the creation date of the user account
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * Gets the reservation status of the user.
     * 
     * @return the reservation status of the user
     */
    public Boolean getReservation() {
        return reservation;
    }

    /**
     * Sets the reservation status of the user.
     * 
     * @param reservation the reservation status of the user
     */
    public void setReservation(Boolean reservation) {
        this.reservation = reservation;
    }

    /**
     * Gets the verification status of the user.
     * 
     * @return the verification status of the user
     */
    public Boolean getVerified() {
        return verified;
    }

    /**
     * Sets the verification status of the user.
     * 
     * @param verified the verification status of the user
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    /**
     * Gets the activity status of the user.
     * 
     * @return the activity status of the user
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Sets the activity status of the user.
     * 
     * @param active the activity status of the user
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setPassword(String pass1, String pass2) throws IllegalAccessException {
        if (pass1.equals(pass2)) {
            this.password = pass1;
        } else {
            throw new IllegalAccessException();
        }
    }

    public Map<String, Object> toDict() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", this.code);
        map.put("name", this.name);
        map.put("surname", this.surname);
        map.put("username", this.username);
        map.put("email", this.email);
        map.put("password", this.password);
        map.put("creationDate", this.creationDate);
        map.put("reservation", this.reservation);
        map.put("verified", this.verified);
        map.put("active", this.active);

        return map;
    }
}
