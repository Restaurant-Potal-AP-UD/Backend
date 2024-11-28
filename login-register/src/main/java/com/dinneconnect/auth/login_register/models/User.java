package com.dinneconnect.auth.login_register.models;

import java.time.LocalDateTime;
import java.util.UUID;

import com.dinneconnect.auth.login_register.DTO.RegisterDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

@Entity
@Table(name = "User")
public class User {

    /**
     * Unique identifier for the user.
     * This value is generated automatically.
     */
    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    /**
     * Name of the user.
     */
    @Column(unique = false, updatable = false)
    private String name;

    /**
     * Surname of the user.
     */
    @Column(unique = false, updatable = false)
    private String surname;

    /**
     * Unique username of the user.
     */
    @Column(unique = true, updatable = true)
    private String username;

    /**
     * Unique email of the user.
     */
    @Column(unique = true, updatable = true)
    private String email;

    /**
     * Hashed password of the user.
     */
    @Column(unique = true, updatable = true)
    private String password;

    /**
     * Creation date of the user account.
     */
    @Column(unique = true, updatable = true)
    private LocalDateTime creationDate;

    /**
     * Reservation status of the user.
     */
    @Column(unique = false, updatable = true)
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
        this.name = user.name;
        this.surname = user.surname;
        this.username = user.username;
        this.email = user.email;
        this.password = user.password;
        this.creationDate = LocalDateTime.now();
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
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the user account.
     * 
     * @param creationDate the creation date of the user account
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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

    public UUID getId() {
        return this.id;

    }
}
