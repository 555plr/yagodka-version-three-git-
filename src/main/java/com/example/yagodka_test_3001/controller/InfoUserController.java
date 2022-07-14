package com.example.yagodka_test_3001.controller;

import com.example.yagodka_test_3001.persist.ImageRepository;
import com.example.yagodka_test_3001.persist.InfoUser;
import com.example.yagodka_test_3001.persist.User;
import com.example.yagodka_test_3001.persist.UserRepository;
import com.example.yagodka_test_3001.service.InfoUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class InfoUserController {

    private  final UserRepository userRepository;

    private  final InfoUserRepository infoUserRepository;

    private final InfoUserService infoUserService;

    private final ImageRepository imageRepository;

    private static final Logger logger =
            LoggerFactory.getLogger(InfoUserController.class);

    @Autowired
    public InfoUserController(UserRepository userRepository, InfoUserRepository infoUserRepository, InfoUserService infoUserService, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.infoUserRepository = infoUserRepository;
        this.infoUserService = infoUserService;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/userinfo")
    public String indexPage(Model model, Principal principal){
        logger.info("User name: {}", principal.getName());
        model.addAttribute("allinfo",infoUserRepository.findByUserUsername(principal.getName()));
        model.addAttribute("newinfo", new InfoUser());
        return "main";
    }

    @PostMapping("/userinfo")
    public  String saveItem(InfoUser infoUser, Principal principal){
        logger.info("User name: {}", principal.getName());
        User user = userRepository.findByUsername(principal.getName()).get();
        infoUser.setUser(user);
        infoUserRepository.save(infoUser);
        return "redirect:/";
    }

    @DeleteMapping("/userinfo/{id}")
    public String deleteItem(@PathVariable("id") Long id){
        infoUserRepository.deleteById(id);

        return "redirect:/";
    }

    @GetMapping("/myprofile")
    public String showMyProfile(Model model, Principal principal){
        logger.info("User name: {}", principal.getName());
         List<InfoUser> infoUserList = infoUserRepository.findByUserUsername(principal.getName());
         InfoUser infouser = infoUserList.get(0);
         model.addAttribute("infouser", infouser);
         imageRepository.findByInfoUserId(infouser.getId());
         model.addAttribute("images", imageRepository.findByInfoUserId(infouser.getId()));

         return "myprofile";

    }

    @PostMapping("/myprofile")
    public String saveMyProfile(@ModelAttribute("infouser") InfoUser infoUser,
                                @RequestParam("file1") MultipartFile file1) throws IOException {
        infoUserService.create(infoUser, file1);

        return "redirect:/myprofile";
    }


}
