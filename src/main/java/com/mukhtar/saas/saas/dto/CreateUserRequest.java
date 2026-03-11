package com.mukhtar.saas.saas.dto;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String email;
    private String password;
    private String role;   // "USER" or "ADMIN"
}