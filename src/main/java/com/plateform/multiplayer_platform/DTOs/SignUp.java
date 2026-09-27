package com.plateform.multiplayer_platform.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUp {
    @NotBlank
    private String username;
    @Email
    private String email;
    @NotBlank
    private String password;
    private String role;
    private String status;
    private String firstName;
    private String lastName;
}
