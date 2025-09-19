package com.Spring.SecurityDemo;

import com.Spring.SecurityDemo.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("hello")
    public String greeting(){
        return "Hello";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("user")
    public String user(){
        return "user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("signIn")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication;

        try{

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername() , loginRequest.getPassword())
            );
        } catch (AuthenticationException exception){

        }
    }


}
