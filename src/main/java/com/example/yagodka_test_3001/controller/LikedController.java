package com.example.yagodka_test_3001.controller;

import com.example.yagodka_test_3001.persist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class LikedController {

    private static final Logger logger =
            LoggerFactory.getLogger(LikedController.class);

    private final LikedUserRepository likedUserRepository;

    private final UserRepository userRepository;

    @Autowired
    public LikedController(LikedUserRepository likedUserRepository, UserRepository userRepository) {
        this.likedUserRepository = likedUserRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/liked")
    public String showLikedUser(Model model, Principal principal){
        logger.info("User name: {}", principal.getName());
        model.addAttribute("likedusers", likedUserRepository.findByUserUsername(principal.getName()));

        return "liked";
    }
}
