package com.dinneconnect.auth.login_register.DTO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dinneconnect.auth.login_register.utilities.JWTUtilities;

/**
 * This module handles data transfer objects (DTOs) for user authentication
 * responses.
 * 
 * @version 1.0
 * @since 2024-11-02
 * @author Sebastian Avendaño Rodriguez
 */
public class LoginResponseDTO {

    private String token;
    private Date caducationDate;

    public LoginResponseDTO() {
    }

    /**
     * Constructs a new LoginResponseDTO with the specified email.
     * The token should be a JWT in the future.
     * 
     * @param uuid the UUID of the user
     */
    public LoginResponseDTO(Long code, String username) {

        Map<String, String> info = new HashMap<String, String>();
        info.put("userID", code.toString());
        info.put("username", username);

        this.token = JWTUtilities.generateToken(info);
        this.caducationDate = new Date(System.currentTimeMillis() + 1800000);

    }

    public Map<String, String> getValue() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("token", this.token);
        data.put("Caducation", this.caducationDate.toString());

        return data;
    }

    public String getToken() {
        return this.token;
    }
}
