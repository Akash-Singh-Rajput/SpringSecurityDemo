package com.Spring.SecurityDemo.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${spring.app.jwtExpirationMs}")
    public long jwtExpirationMs;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwtSecret}")
    public String jwtSecret;

    // Getting JWT from Http Header
    public String getJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header : {}" , bearerToken);
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);

        }
        return null;
    }

    // Generate Token from UserDetail
    public String generateTokenFromUsername(UserDetails userDetails){
        String username = userDetails.getUsername();

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    // Get username from JWT token
     public String getUsernameFromJwtToken(String token){
        return Jwts
                /* Jwts is the entry point from the jjwt library */

                .parser()
                /* creates a parser builder for reading/validating a JWT */

                .verifyWith( (SecretKey) key())
                /*
                   tells the parser which key to use for verifying the token’s signature.
                   This ensures the token hasn’t been tampered with.
                */


                .build()

                .parseSignedClaims(token)
                // returns Jws<Claims> object (singed claims) if the token is not expired
                // and invalid else it will throw exception

                .getPayload().getSubject();  // get the information and returns the username
     }

    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

     public boolean validateJwtToken(String authToken){

         try {
             System.out.println("Validate");
             Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
             return true;
         } catch (MalformedJwtException e) {
             logger.error("Invalid JWT token: {}", e.getMessage());
         } catch (ExpiredJwtException e) {
             logger.error("JWT token is expired: {}", e.getMessage());
         } catch (UnsupportedJwtException e) {
             logger.error("JWT token is unsupported: {}", e.getMessage());
         } catch (IllegalArgumentException e) {
             logger.error("JWT claims string is empty: {}", e.getMessage());
         }

        return false;
     }
}
