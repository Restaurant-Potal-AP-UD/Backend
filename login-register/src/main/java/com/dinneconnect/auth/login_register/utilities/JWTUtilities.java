package com.dinneconnect.auth.login_register.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class JWTUtilities {

    private static final String SECRET_KEY = "25d3c96542daa0310afb85d88c2d380d4458e19af73ed208f96947c706adee06";

    public static String generateToken(Map<String, String> subject) {

        JwtBuilder jwt = Jwts.builder();
        jwt.subject(subject.get("userEmail"));
        jwt.issuedAt(new Date());
        jwt.expiration(new Date(System.currentTimeMillis() + 1800000));
        jwt.issuer("dinneconect.auth.system");  
        jwt.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), Jwts.SIG.HS256);
        
        return jwt.compact();
                
    }

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
            System.out.println("El token ha expirado: " + e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            System.out.println("El formato del token no es compatible: " + e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            System.out.println("El token está mal formado: " + e.getMessage());
            throw e;
        } catch (SignatureException e) {
            System.out.println("La firma del token no es válida: " + e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            System.out.println("El token está vacío o es nulo: " + e.getMessage());
            throw e;
        }
    }
}
