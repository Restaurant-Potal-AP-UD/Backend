package com.dinneconnect.auth.login_register.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dinneconnect.auth.login_register.models.User;

import jakarta.transaction.Transactional;

/**
 * Repository interface for performing CRUD operations on User entities.
 * This interface extends JpaRepository, providing methods to manage User
 * entities in the database, including custom query methods for updating
 * user information.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Retrieves a User entity by its email address.
     *
     * @param email the email address of the user
     * @return the User entity associated with the given email, or null if no user
     *         is found
     */
    User findByEmail(String email);

    /**
     * Updates the primary information of a User entity identified by its unique ID.
     * This includes updating the user's name, surname, username, and email address.
     *
     * @param id       the unique identifier of the user to be updated
     * @param name     the new name of the user
     * @param surname  the new surname of the user
     * @param username the new username of the user
     * @param email    the new email address of the user
     * @return the number of rows affected by the update
     */
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name = :name, u.surname = :surname, u.username = :username, u.email = :email WHERE u.id = :id")
    int updatePrimaryInfo(@Param("id") UUID id, @Param("name") String name, @Param("surname") String surname,
            @Param("username") String username, @Param("email") String email);

    /**
     * Updates the password of a User entity identified by its unique ID.
     *
     * @param id       the unique identifier of the user whose password is to be
     *                 updated
     * @param password the new password to be set for the user
     * @return the number of rows affected by the update
     */
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    int updatePassword(@Param("id") UUID id, @Param("password") String password);
}
