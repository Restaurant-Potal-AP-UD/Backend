/**
 * This class performs unit tests for the UserService class.
 * The tests verify the functionality of various UserService methods such as:
 * - getAllUsers
 * - getUserById
 * - getUserByEmail
 * - updatePassword
 * - createUser
 * - deleteUserById
 * 
 * The tests use Mockito to mock dependencies and simulate various scenarios.
 * 
 * Dependencies:
 * - JUnit 5
 * - Mockito
 * 
 * Created by: sebas
 */
package com.dinneconnect.auth.login_register.userServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.dinneconnect.auth.login_register.DTO.RegisterDTO;
import com.dinneconnect.auth.login_register.models.User;
import com.dinneconnect.auth.login_register.repository.UserRepository;
import com.dinneconnect.auth.login_register.services.UserService;

/**
 * Unit tests for the UserService class.
 */
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UUID userId;

    /**
     * Sets up the test environment before each test.
     * Initializes mocks and creates a sample user.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RegisterDTO user_dto = new RegisterDTO("John", "Doe", "john.doe", "john.doe@example.com", "password123");
        user = new User(user_dto);
    }

    /**
     * Tests retrieving all users.
     * Verifies that the list contains the expected users.
     */
    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
        verify(userRepository, times(1)).findAll();
    }

    /**
     * Tests retrieving a user by ID (success case).
     * Verifies that the correct user is returned.
     */
    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(userId);

        assertNotNull(foundUser);
        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findById(userId);
    }

    /**
     * Tests retrieving a user by ID (not found case).
     * Verifies that a RuntimeException is thrown.
     */
    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getUserById(userId));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    /**
     * Tests retrieving a user by email.
     * Verifies that the correct user is returned.
     */
    @Test
    void testGetUserByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        User foundUser = userService.getUserByEmail(user.getEmail());

        assertNotNull(foundUser);
        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    /**
     * Tests updating the user password (success case).
     * Verifies that the response indicates a successful update.
     */
    @Test
    void testUpdatePassword_Success() {
        String newPassword = "newPassword123";
        when(userRepository.updatePassword(userId, newPassword)).thenReturn(1);

        ResponseEntity<String> response = userService.updatePassword(userId, newPassword);

        assertNotNull(response);
        assertEquals("User updated successfully", response.getBody());
        verify(userRepository, times(1)).updatePassword(userId, newPassword);
    }

    /**
     * Tests updating the user password (not found case).
     * Verifies that a RuntimeException is thrown.
     */
    @Test
    void testUpdatePassword_NotFound() {
        String newPassword = "newPassword123";
        when(userRepository.updatePassword(userId, newPassword)).thenReturn(0);

        Exception exception = assertThrows(RuntimeException.class,
                () -> userService.updatePassword(userId, newPassword));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).updatePassword(userId, newPassword);
    }

    /**
     * Tests creating a new user.
     * Verifies that the user is successfully created.
     */
    @Test
    void testCreateUser() {
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user, createdUser);
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Tests deleting a user by ID (not found case).
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void testDeleteUserById_NotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> userService.deleteUserById(userId));

        assertEquals("User with ID " + userId + " does not exist.", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(0)).deleteById(userId);
    }
}
