package com.mukhtar.saas.saas.dto;

import lombok.Data;

@Data
public class RegisterCompanyRequest {
    private String companyName;
    private String email;
    private String password;
}