package com.example.yagodka_test_3001.service;

import com.example.yagodka_test_3001.persist.InfoUser;
import com.example.yagodka_test_3001.persist.User;
import com.example.yagodka_test_3001.persist.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void create(UserWrapper userWrapper, InfoUser infoUser){
        User user = new User();
        user.setUsername(userWrapper.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userWrapper.getPassword()));
        user.setInfouser(infoUser);
        userRepository.save(user);
    }
}