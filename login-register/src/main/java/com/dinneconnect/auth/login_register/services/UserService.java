package com.dinneconnect.auth.login_register.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinneconnect.auth.login_register.DTO.UpdatePrimaryInfoDTO;
import com.dinneconnect.auth.login_register.DTO.UserResponseDTO;
import com.dinneconnect.auth.login_register.models.User;
import com.dinneconnect.auth.login_register.repository.BaseRepository;

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

    private final BaseRepository userRepository;

    /**
     * Constructs a new UserService with the specified UserRepository.
     * 
     * @param userRepository the repository for user data
     */
    @Autowired
    public UserService(BaseRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a list of all users.
     * 
     * @return a list of all users
     */
    public List<UserResponseDTO> getAllUsers() {
        List<Map<String, Object>> users = userRepository.getEntities();

        if (users.isEmpty()) {
            return Collections.emptyList();
        }

        System.out.println(users);

        List<UserResponseDTO> result = new ArrayList<>();
        for (Map<String, Object> map : users) {

            UserResponseDTO user = new UserResponseDTO(
                    (Long) map.get("code"),
                    (String) map.get("name"),
                    (String) map.get("surname"),
                    (String) map.get("username"),
                    (String) map.get("email"),
                    (String) map.get("creationDate"));
            result.add(user);
        }
        return result;
    }

    /**
     * Retrieves a user by their ID.
     * 
     * @param id the long of the user
     * @return the user
     * @throws RuntimeException if the user is not found
     */
    public UserResponseDTO getUserById(Long id) {
        Map<String, Object> user = userRepository.getEntityByCode(id);
        if (user != null) {

            return new UserResponseDTO(
                    (Long) user.get("code"),
                    (String) user.get("name"),
                    (String) user.get("surname"),
                    (String) user.get("username"),
                    (String) user.get("email"),
                    (String) user.get("creationDate"));
        }
        return null;
    }

    /**
     * Retrieves a user by their email address.
     * 
     * @param email the email of the user
     * @return the user with the specified email
     */
    public UserResponseDTO getUserByEmail(String email) {
        Map<String, Object> user = userRepository.getEntityByField("email", email);
        if (user != null) {

            return new UserResponseDTO(
                    (Long) user.get("code"),
                    (String) user.get("name"),
                    (String) user.get("surname"),
                    (String) user.get("username"),
                    (String) user.get("email"),
                    (String) user.get("creationDate"));
        }
        return null;
    }

    /**
     * Retrieves a user by their username.
     * 
     * @param username
     * @return the user with the specified username
     */
    public UserResponseDTO getUserByUsername(String username, String password) {

        Map<String, Object> user = userRepository.getEntityByField("username", username);

        if ((user != null && ((String) user.get("password")).equals(password))) {

            return new UserResponseDTO(
                    (Long) user.get("code"),
                    (String) user.get("name"),
                    (String) user.get("surname"),
                    (String) user.get("username"),
                    (String) user.get("email"),
                    (String) user.get("creationDate"));
        }
        return null;
    }

    /**
     * Updates the primary information of a user.
     * 
     * @param id   the Long of the user to update
     * @param user the DTO containing the new primary information
     * @return a ResponseEntity indicating the result of the update operation
     * @throws RuntimeException if the user is not found
     */
    public Map<String, Boolean> updatePrimaryInfo(Long id, UpdatePrimaryInfoDTO updateDTO) {

        if (userRepository.getEntityByCode(id).isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Map<String, Boolean> response;
        Map<String, Object> updates = new HashMap<>();

        if (updateDTO.getName() != null) {
            updates.put("name", updateDTO.getName());
        }
        if (updateDTO.getSurname() != null) {
            updates.put("surname", updateDTO.getSurname());
        }
        if (updateDTO.getUsername() != null) {
            updates.put("username", updateDTO.getUsername());
        }
        if (updateDTO.getEmail() != null) {
            updates.put("email", updateDTO.getEmail());
        }

        if (updates.isEmpty()) {
            response = new HashMap<>();
            response.put("success", false);
            return response;
        }

        Map<String, Object> result = userRepository.updateEntity(id, updates);

        if (!(boolean) result.get("success")) {
            response = new HashMap<>();
            response.put("success", false);
            return response;
        }

        response = new HashMap<>();
        response.put("success", false);
        return response;
    }

    /**
     * Updates the password of a user.
     * 
     * @param id       the UUID of the user whose password is to be updated
     * @param password the new password
     * @return a ResponseEntity indicating the result of the update operation
     * @throws RuntimeException if the user is not found
     */

    public Map<String, Boolean> updatePassword(Long id, String password) {
        Map<String, Boolean> response = new HashMap<>();
        Map<String, Object> updates = new HashMap<>();
        updates.put("password", password);

        Map<String, Object> update = userRepository.updateEntity(id, updates);

        if (!(boolean) update.get("success")) {
            throw new RuntimeException("User not found");
        }
        response.put("success", false);
        return response;
    }

    /**
     * Creates a new user.
     * 
     * @param user the user to create
     * @return the created user
     */
    public Map<String, Boolean> createUser(User user) {
        return userRepository.postEntity(user);
    }

    /**
     * Deletes a user by their ID.
     * 
     * @param id the UUID of the user to delete
     * @return true if the user was deleted successfully; false otherwise
     * @throws IllegalArgumentException if the user with the specified ID does not
     *                                  exist
     */
    public Map<String, Object> deleteUserById(Long id) {
        if (userRepository.getEntityByCode(id) != null) {
            Map<String, Object> user = userRepository.deleteEntityByCode(id);
            return user;
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            return response;
        }
    }

}