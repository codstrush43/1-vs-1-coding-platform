package com.plateform.multiplayer_platform.Controller.UserController;

import org.springframework.web.bind.annotation.RestController;

import com.plateform.multiplayer_platform.DTOs.UserDTO;
import com.plateform.multiplayer_platform.Entity.User;
import com.plateform.multiplayer_platform.Repository.UserRepository;
import com.plateform.multiplayer_platform.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController 
@RequestMapping("/api/user")
public class UserController {

    @Autowired 
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> postMethodName(@RequestBody UserDTO userDto) {
        
        return ResponseEntity.ok().body(userService.saveUser(userDto));
    }
    

}
