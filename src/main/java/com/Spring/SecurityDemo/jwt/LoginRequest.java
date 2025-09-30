package com.Spring.SecurityDemo.jwt;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {

    String username;
    String password;


}
