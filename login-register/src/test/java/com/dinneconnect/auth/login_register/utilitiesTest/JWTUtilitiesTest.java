/**
 * This class performs unit tests for the JWTUtilities class.
 * The tests verify the functionality of the token verification methods,
 * handling cases such as valid tokens, expired tokens, malformed tokens,
 * tokens with format errors, and tokens with signature errors.
 * 
 * Dependencies:
 * - JUnit 5
 * - Mockito
 * 
 * Created by: sebas
 */
package com.dinneconnect.auth.login_register.utilitiesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dinneconnect.auth.login_register.utilities.JWTUtilities;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

/**
 * Unit tests for the JWTUtilities class.
 */
public class JWTUtilitiesTest {

    @Mock
    private JWTUtilities jwtUtilities;

    private Map<String, Object> tokenMap;

    private String tokenGood;
    private String tokenExpired;
    private String tokenFormat;
    private String tokenMalformed;
    private String tokenSignature;

    /**
     * Sets up the test environment before each test.
     * Initializes the token map and sample tokens for testing.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.tokenMap = new HashMap<String, Object>() {
            {
                put("sub", "8d83e4b9-9c2c-4080-852c-bd91579e8805");
                put("name", "John Doe");
                put("iat", 1692572206L);
                put("exp", 4857170206L);
            }
        };

        this.tokenGood = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI4ZDgzZTRiOS05YzJjLTQwODAtODUyYy1iZDkxNTc5ZTg4MDUiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE2OTI1NzIyMDYsImV4cCI6NDg1NzE3MDIwNn0.Rn5qD1Emn7qJ9-t6EgMIy-YBJE5zOnYT_PeaH9B_HTE";
        this.tokenExpired = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI4ZDgzZTRiOS05YzJjLTQwODAtODUyYy1iZDkxNTc5ZTg4MDUiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE2OTI1NzIyMDYsImV4cCI6MTY5MjU3MjgwNn0.010HzQyO8EJOB1aHBV9yk4xSBAejAhS9JzQ6vyID3kw";
        this.tokenFormat = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI4ZDgzZTRiOS05YzJjLTQwODAtODUyYy1iZDkxNTc5ZTg4MDUiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE2OTI1NzIyMDYsImV4cCI6MTY5MjU3MjgwNn0010HzQyO8EJ.OB1aHBV9yk4xSBAejAhS9JzQ6vyID3kw";
        this.tokenMalformed = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI4ZDgzZTRiOS05YzJjLTQwODAtODUyYy1iZDkxNTc5ZTg4MDUiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE2OTI1NzIyMDYsImV4cCI6MTY5MjU3MjgwNn0010HzQyO8EJOB1aHBV9yk4xSBAejAhS9JzQ6vyID3kw";
        this.tokenSignature = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI4ZDgzZTRiOS05YzJjLTQwODAtODUyYy1iZDkxNTc5ZTg4MDUiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE2OTI1NzIyMDYsImV4cCI6MTY5MjU3MjgwNn0.wrongsignature";
    }

    /**
     * Tests verifying a valid JWT token.
     * Verifies that the token body matches the expected token map.
     */
    @Test
    void testParseJWT() {
        Map<String, Object> tokenBody = JWTUtilities.verifyToken(this.tokenGood);
        assertEquals(tokenBody, tokenMap);
    }

    /**
     * Tests verifying an expired JWT token.
     * Verifies that an ExpiredJwtException is thrown.
     */
    @Test
    void testParseJWT_ExpiredTokenError() {
        assertThrows(ExpiredJwtException.class, () -> {
            JWTUtilities.verifyToken(this.tokenExpired);
        });
    }

    /**
     * Tests verifying a JWT token with format errors.
     * Verifies that a SignatureException is thrown.
     */
    @Test
    void testParseJWT_TokenFormatError() {
        assertThrows(SignatureException.class, () -> {
            JWTUtilities.verifyToken(this.tokenFormat);
        });
    }

    /**
     * Tests verifying a malformed JWT token.
     * Verifies that a MalformedJwtException is thrown.
     */
    @Test
    void testParseJWT_TokenMalformedError() {
        assertThrows(MalformedJwtException.class, () -> {
            JWTUtilities.verifyToken(this.tokenMalformed);
        });
    }

    /**
     * Tests verifying a JWT token with signature errors.
     * Verifies that a SignatureException is thrown.
     */
    @Test
    void testParseJWT_SignatureError() {
        assertThrows(SignatureException.class, () -> {
            JWTUtilities.verifyToken(this.tokenSignature);
        });
    }

    /**
     * Tests verifying a null JWT token.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void testParseJWT_NullError() {
        assertThrows(IllegalArgumentException.class, () -> {
            JWTUtilities.verifyToken("");
        });
    }
}
