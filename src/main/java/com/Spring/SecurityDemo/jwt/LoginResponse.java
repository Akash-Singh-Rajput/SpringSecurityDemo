package com.Spring.SecurityDemo.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    String username;
    List<String> roles;
    String jwtToken;


}
