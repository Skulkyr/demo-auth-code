package org.pogonin.authservice.core.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.pogonin.authservice.db.entity.User;

import java.util.function.Function;


/**
 * The interface Jwt token service.
 */
public interface TokenService {

    /**
     * Extract email string.
     *
     * @param token the token
     * @return the string
     * @throws ExpiredJwtException the expired jwt exception
     */
    String extractEmail(String token) throws ExpiredJwtException;

    /**
     * Extract claim t.
     *
     * @param <T>            the type parameter
     * @param token          the token
     * @param claimsResolver the claims resolver
     * @return the t
     * @throws ExpiredJwtException the expired jwt exception
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws ExpiredJwtException;

    /**
     * Generate token string.
     *
     * @param user the user
     * @return the string
     */
    String generateToken(User user);

}
