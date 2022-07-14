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
import java.util.List;
import java.util.Optional;

@Controller
public class IndexController {

    private static final Logger logger =
            LoggerFactory.getLogger(IndexController.class);

    private final InfoUserRepository infoUserRepository;

    private final UserRepository userRepository;

    private final LikedUserRepository likedUserRepository;

    @Autowired
    public IndexController(InfoUserRepository infoUserRepository, UserRepository userRepository, LikedUserRepository likedUserRepository) {
        this.infoUserRepository = infoUserRepository;
        this.userRepository = userRepository;
        this.likedUserRepository = likedUserRepository;
    }

    @GetMapping("/hello")
    public String successfullyCreated(){
        return "hello";
    }

    @GetMapping("/")
    public String showMainPage(Model model){
        model.addAttribute("people", infoUserRepository.findAll());

        return "main";
    }

//    @GetMapping("/mans")
//    public String showPageWithMans(){
//
//        return null;
//    }

    @GetMapping("/id{id}")
    public String showPageHuman(@PathVariable("id") Long id, Model model){
        Optional<InfoUser> optional = infoUserRepository.findById(id);
        InfoUser infoUser = optional.get();
        model.addAttribute("certain", infoUser);
        model.addAttribute("image", infoUser.getImage());
        return "pagehuman";
    }

    @GetMapping("/main/mans")
    public String showAllMans(Model model){
        List<InfoUser> infoUserList = infoUserRepository.findByGender("мужской");
        model.addAttribute("mans", infoUserList);

        return "mans";
    }

    @GetMapping("/main/womans")
    public String showAllWomans(Model model){
        List<InfoUser> infoUserList = infoUserRepository.findByGender("женский");
        model.addAttribute("womans", infoUserList);

        return "womans";
    }

    @GetMapping("/main/other")
    public String showAllOther(Model model){
        List<InfoUser> infoUserList = infoUserRepository.findByGender("другое");
        model.addAttribute("others", infoUserList);

        return "others";
    }



    @PostMapping("/id{id}")
    public  String saveLikedUser(@ModelAttribute("certain") InfoUser infoUser,
                                 LikedUser likedUser, Principal principal, Model model){
        logger.info("User name: {}", principal.getName());
        User user = userRepository.findByUsername(principal.getName()).get();

        likedUser.setNickname(infoUser.getNickname());
        likedUser.setGender(infoUser.getGender());
        likedUser.setAge(infoUser.getAge());
        likedUser.setInfo(infoUser.getInfo());
        likedUser.setUser(user);
        likedUserRepository.save(likedUser);

        model.addAttribute("certain", infoUser);

        return "redirect:/";
    }
    //////// отображение списка сохраненных в закладки


    @GetMapping("/site")
    public String showosaite(){

        return "osaite";
    }




}
