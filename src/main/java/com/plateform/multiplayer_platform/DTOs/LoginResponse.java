package com.plateform.multiplayer_platform.DTOs;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    
    public void setToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}
