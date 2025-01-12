package com.dinneconnect.auth.login_register.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dinneconnect.auth.login_register.DTO.UpdatePrimaryInfoDTO;
import com.dinneconnect.auth.login_register.models.User;
import com.dinneconnect.auth.login_register.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Service class for managing user-related operations.
 * This class provides methods to create, retrieve, update, and delete users.
 * It interacts with the UserRepository to perform these operations.
 * 
 * <p>
 * The UserService class is annotated with {@link Service}, indicating that it
 * is a service
 * component in the Spring framework. It is responsible for business logic
 * related to users.
 * </p>
 * 
 * @author Sebastian Avendaño Rodriguez
 * @since 2024/11/03
 * @version 1.0
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructs a new UserService with the specified UserRepository.
     * 
     * @param userRepository the repository for user data
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a list of all users.
     * 
     * @return a list of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     * 
     * @param id the UUID of the user
     * @return the user
     * @throws RuntimeException if the user is not found
     */
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Retrieves a user by their email address.
     * 
     * @param email the email of the user
     * @return the user with the specified email
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieves a user by their username.
     * 
     * @param username
     * @return the user with the specified username
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Updates the primary information of a user.
     * 
     * @param id   the UUID of the user to update
     * @param user the DTO containing the new primary information
     * @return a ResponseEntity indicating the result of the update operation
     * @throws RuntimeException if the user is not found
     */
    @Transactional
    public ResponseEntity<String> updatePrimaryInfo(UUID id, UpdatePrimaryInfoDTO updateDTO) {
        // Primero obtenemos el usuario actual
        if (userRepository.findById(id).isEmpty()) {
            throw new RuntimeException("User not found");
        }
        ;

        // Creamos un mapa para almacenar solo los campos que necesitan actualización
        Map<String, String> fieldsToUpdate = new HashMap<>();

        if (updateDTO.getName() != null) {
            fieldsToUpdate.put("name", updateDTO.getName());
        }
        if (updateDTO.getSurname() != null) {
            fieldsToUpdate.put("surname", updateDTO.getSurname());
        }
        if (updateDTO.getUsername() != null) {
            fieldsToUpdate.put("username", updateDTO.getUsername());
        }
        if (updateDTO.getEmail() != null) {
            fieldsToUpdate.put("email", updateDTO.getEmail());
        }

        // Si no hay campos para actualizar, retornamos temprano
        if (fieldsToUpdate.isEmpty()) {
            return ResponseEntity.ok().body("No fields to update");
        }

        // Actualizamos solo los campos no nulos
        int affectedRows = userRepository.updatePrimaryInfo(
                id,
                fieldsToUpdate.get("name"),
                fieldsToUpdate.get("surname"),
                fieldsToUpdate.get("username"),
                fieldsToUpdate.get("email"));

        if (affectedRows == 0) {
            throw new RuntimeException("Failed to update user");
        }

        return ResponseEntity.ok().body("User updated successfully");
    }

    /**
     * Updates the password of a user.
     * 
     * @param id       the UUID of the user whose password is to be updated
     * @param password the new password
     * @return a ResponseEntity indicating the result of the update operation
     * @throws RuntimeException if the user is not found
     */
    @Transactional
    public ResponseEntity<String> updatePassword(UUID id, String password) {
        int affectedRows = userRepository.updatePassword(id, password);
        if (affectedRows == 0) {
            throw new RuntimeException("User not found");
        }
        return ResponseEntity.ok().body("User updated successfully");
    }

    /**
     * Creates a new user.
     * 
     * @param user the user to create
     * @return the created user
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Deletes a user by their ID.
     * 
     * @param id the UUID of the user to delete
     * @return true if the user was deleted successfully; false otherwise
     * @throws IllegalArgumentException if the user with the specified ID does not
     *                                  exist
     */
    public void deleteUserById(UUID id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with ID " + id + " does not exist.");
        }
    }

}