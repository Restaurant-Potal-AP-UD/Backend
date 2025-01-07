package com.dinneconnect.auth.login_register.utilities;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for handling JSON Web Tokens (JWT).
 * This class provides methods to generate and verify JWTs.
 * It includes functionality for setting token claims, expiration, and signing.
 * 
 * <p>
 * The JWTUtilities class uses a secret key for signing tokens and provides
 * methods to handle exceptions related to token verification.
 * </p>
 * 
 * @author Sebastian Avendaño Rodriguez
 * @since 2024/11/24
 * @version 1.0
 */
public class JWTUtilities {

    private static final String SECRET_KEY = "25d3c96542daa0310afb85d88c2d380d4458e19af73ed208f96947c706adee06";

    /**
     * Generates a JSON Web Token (JWT) for the specified subject.
     * 
     * @param subject a map containing the subject information, including userID
     * @return a compact JWT string
     */
    public static String generateToken(Map<String, String> subject) {
        JwtBuilder jwt = Jwts.builder();
        jwt.subject(subject.get("userID"));
        jwt.issuedAt(new Date());
        jwt.expiration(new Date(System.currentTimeMillis() + 1800000)); // Token valid for 30 minutes
        jwt.issuer("dinneconect.auth.system");
        jwt.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), Jwts.SIG.HS256);

        return jwt.compact();
    }

    /**
     * Verifies the given JWT and returns the claims contained within it.
     * 
     * @param token the JWT to be verified
     * @return a map containing the claims extracted from the token
     * @throws ExpiredJwtException      if the token has expired
     * @throws UnsupportedJwtException  if the token format is not supported
     * @throws MalformedJwtException    if the token is malformed
     * @throws SignatureException       if the token signature is invalid
     * @throws IllegalArgumentException if the token is null or empty
     */
    public static Map<String, Object> verifyToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Map<String, Object> claimsMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                claimsMap.put(entry.getKey(), entry.getValue());
            }

            return claimsMap;

        } catch (ExpiredJwtException e) {
            System.out.println("The token has expired");
            throw e;
        } catch (UnsupportedJwtException e) {
            System.out.println("The token format is not supported");
            throw e;
        } catch (MalformedJwtException e) {
            System.out.println("The token is malformed");
            throw e;
        } catch (SignatureException e) {
            System.out.println("The token signature is invalid");
            throw e;
        } catch (IllegalArgumentException e) {
            System.out.println("The token is empty or null");
            throw e;
        }
    }
}
