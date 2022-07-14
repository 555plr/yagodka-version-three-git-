package com.example.yagodka_test_3001.controller;

import com.example.yagodka_test_3001.persist.*;
import com.example.yagodka_test_3001.service.UserService;
import com.example.yagodka_test_3001.service.UserWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class LoginController {

//    private final InfoUserRepository infoUserRepository;
private static final Logger logger =
        LoggerFactory.getLogger(LoginController.class);

    private final InfoUserRepository infoUserRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final ImageRepository imageRepository;

    @Autowired
    public LoginController(InfoUserRepository infoUserRepository, UserRepository userRepository, UserService userService, ImageRepository imageRepository) {
        this.infoUserRepository = infoUserRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/login")
    public String loginPage(){

        return "login";
    }

    @GetMapping("/register")
    public String registerShowForm(Model model){
        model.addAttribute("user", new UserWrapper());

        return "register";
    }

    @PostMapping("/edit")
    public String registerSending(Model model, @Valid @ModelAttribute("user") UserWrapper userWrapper, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "register";
        }
        if (!userWrapper.getPassword().equals(userWrapper.getRepeatPassword())){
            bindingResult.rejectValue("password", "", "Пароли не совпадают");
            return "register";
        }

        InfoUser infoUser = new InfoUser();
        Image image = new Image();
        imageRepository.save(image);
        infoUser.setImage(image);
        infoUserRepository.save(infoUser);
        userService.create(userWrapper, infoUser);
        model.addAttribute("obinfouser", infoUser);

        return "infouser";
    }


    @PatchMapping("/edit")
    public String editInfo(@ModelAttribute("obinfouser") @Valid InfoUser infoUser, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "infouser";
        }
        Image image = new Image();
        imageRepository.save(image);
        infoUser.setImage(image);
        infoUserRepository.save(infoUser);
        return "redirect:/hello";
    }



}
