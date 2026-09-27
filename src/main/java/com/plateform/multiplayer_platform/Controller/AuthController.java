package com.plateform.multiplayer_platform.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plateform.multiplayer_platform.DTOs.AuthRequest;
import com.plateform.multiplayer_platform.DTOs.LoginResponse;
import com.plateform.multiplayer_platform.DTOs.SignUp;
import com.plateform.multiplayer_platform.Entity.User;
import com.plateform.multiplayer_platform.JWTUtil.JwtUtil;
import com.plateform.multiplayer_platform.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@RequestBody @Valid SignUp signUpRequest)
    {
        User user = modelMapper.map(signUpRequest,User.class);
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody @Valid AuthRequest authRequest)
    { 
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
        );
        String token = jwtUtil.generateToken(authRequest.getUsername());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return ResponseEntity.ok(loginResponse);
    }
   
    @PostMapping("/hello")
    public String hello() {
        return "Hello";
    }

}
