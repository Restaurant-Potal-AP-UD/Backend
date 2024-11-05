package com.dinneconnect.auth.login_register.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinneconnect.auth.login_register.models.User;
import com.dinneconnect.auth.login_register.repository.UserRepository;

/**
 * Service class for managing users.
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
     * @param id the ID of the user
     * @return the user
     * @throws RuntimeException if the user is not found
     */
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
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
}
