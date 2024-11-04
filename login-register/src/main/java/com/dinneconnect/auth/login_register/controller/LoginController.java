package com.dinneconnect.auth.login_register.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinneconnect.auth.login_register.DTO.UserRequestDTO;
import com.dinneconnect.auth.login_register.models.User;
import com.dinneconnect.auth.login_register.services.UserService;
import com.dinneconnect.auth.login_register.utilities.UserUtilities;
import com.dinneconnect.auth.login_register.DTO.LoginRequestDTO;
import com.dinneconnect.auth.login_register.DTO.LoginResponseDTO;

@RestController
@RequestMapping("/api")
public class LoginController {

    private UserService userService;
    private UserUtilities utilities;

    @PostMapping("/auth-user/")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO user) {
        return null;
    }

    @GetMapping("/users/")
    public List<UserRequestDTO> getUsers() {
        List<User> users = userService.getAllUsers();
        return utilities.UsersToUserDTOs(users);
    }

    @GetMapping("/user/{id}")
    public UserRequestDTO getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return utilities.UserToUserDTO(user);
        } catch (RuntimeException e) {
            return new UserRequestDTO();
        }
    }
}