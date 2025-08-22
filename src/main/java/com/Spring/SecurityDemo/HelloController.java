package com.Spring.SecurityDemo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

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
}
