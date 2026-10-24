package com.plateform.multiplayer_platform.ServiceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.DTOs.UserDTO;
import com.plateform.multiplayer_platform.Entity.User;
import com.plateform.multiplayer_platform.Repository.UserRepository;
import com.plateform.multiplayer_platform.Service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired 
    private ModelMapper modelMapper;

    public User saveUser(UserDTO userDto) {

        User user = modelMapper.map(userDto,User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
