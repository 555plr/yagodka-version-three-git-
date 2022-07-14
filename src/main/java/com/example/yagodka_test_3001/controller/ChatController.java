package com.example.yagodka_test_3001.controller;

import com.example.yagodka_test_3001.persist.*;
import com.example.yagodka_test_3001.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ChatController {

    private static final Logger logger =
            LoggerFactory.getLogger(ChatController.class);

    private final InfoUserRepository infoUserRepository;

    private final UserRepository userRepository;

    private final ChatRepository chatRepository;

    private final MessageRepository messageRepository;

    private final UserService userService;

    @Autowired
    public ChatController(InfoUserRepository infoUserRepository, UserRepository userRepository, ChatRepository chatRepository, MessageRepository messageRepository, UserService userService) {
        this.infoUserRepository = infoUserRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @GetMapping("/sendtoid{id}")
    public String OpenSendTo(@PathVariable("id") Long id, Model model){
        model.addAttribute("currentchat", new Chat());
        model.addAttribute("currentmessage", new Message());
        model.addAttribute("superid", id);

        return "sendpage";
    }

    @PostMapping("/sendtoid{id}")
    public String OpenSendTo(Model model, @PathVariable("id") Long id, @ModelAttribute("currentchat") Chat chat,
                             @ModelAttribute("currentmessage") Message message,
                             Principal principal){
        Optional<User> optional1 = userRepository.findByUsername(principal.getName());
        User user10 = optional1.get();
        String nameSender10 = user10.getInfouser().getNickname();

        chat.setWhoUsername(user10.getUsername());
        chat.setWhomUsername(userRepository.findById(id).get().getUsername());
        chat.setWho(nameSender10);
        chatRepository.save(chat);
        message.setChat(chat);
        message.setWho(nameSender10);

        chat.addMessageToChat(message);
        messageRepository.save(message);
        Chat fullChat = chatRepository.save(chat);
        logger.info("User name: {}", principal.getName());
        User user = userRepository.findByUsername(principal.getName()).get();
        user.addChatsToUser(fullChat);
        userRepository.save(user);
        Optional<User> toUser = userRepository.findById(id);
        User user1 = toUser.get();
        user1.addChatsToUser(fullChat);
        userRepository.save(user1);


        return "redirect:/chatnumber"+fullChat.getId();
    }

    @GetMapping("/chats")
    public String showMyChats(Model model, Principal principal) {
        logger.info("User name: {}", principal.getName());
        Optional<User> optional = userRepository.findByUsername(principal.getName());
        User user = optional.get();
        List<Chat> chatList = user.getChats();

        Long idd = null;
        for (Chat element : chatList) {
            if (element.getWho().equals(user.getInfouser().getNickname())) {

                for (User oneuser : element.getUsers()) {

                    if (!oneuser.getInfouser().getNickname().equals(user.getInfouser().getNickname())) {
                        element.setWho(oneuser.getInfouser().getNickname());
                    }
                }
            }
        }


////
////        for (Chat element : chatList){
////            List<User> listuseer = element.getUsers();
////        }
//
//        model.addAttribute("listuuusers", listuseer);


        model.addAttribute("userchats", chatList);
        model.addAttribute("user", user);



        return "chats";
    }


    @GetMapping("/chatnumber{id}")
    public String showCurrentChat(@PathVariable("id") Long id, Model model, Principal principal){
        List<Message> messages = messageRepository.findByChat_Id(id);
        model.addAttribute("messages", messages);


        User sender = userRepository.findByUsername(principal.getName()).get();
        String nicknamesender = sender.getInfouser().getNickname();

        Message message = new Message();
        message.setWho(nicknamesender);
        message.setChat(chatRepository.findById(id).get());
        model.addAttribute("message", message);

        model.addAttribute("chatid", id);

        return "chat";
    }

    @PostMapping("/chatnumber{id}")
    public String messageToChat(@PathVariable("id") Long id, @ModelAttribute("message") Message message, Principal principal){

         Chat chat = chatRepository.findById(id).get();


         message.setChat(chat);
         messageRepository.save(message);
         chat.addMessageToChat(message);
         chatRepository.save(chat);

//        String name1 = chat.getWhoUsername();
//        String name2 =chat.getWhomUsername();
//        List<Chat> list1= userRepository.findByUsername(name1).get().getChats();
//        List<Chat> list2= userRepository.findByUsername(name1).get().getChats();

//        for (Chat element : list1){
//            if (!element.getId().equals(id)){
//
//                User user111 = userRepository.findByUsername(name1).get();
//                list1.add(chat);
//                user111.setChats(list1);
//                userRepository.save(user111);
//
//            }
//
//        }
//
//        for (Chat element : list2){
//            if (!element.getId().equals(id)){
//
//                User user222 = userRepository.findByUsername(name1).get();
//                list2.add(chat);
//                user222.setChats(list1);
//                userRepository.save(user222);
//            }
//
//        }



        return "redirect:/chatnumber{id}";
    }

    @DeleteMapping("/chatnumber{id}")
    public String deleteChat(@PathVariable("id") Long id, Principal principal){
        User user = userRepository.findByUsername(principal.getName()).get();
        List<Chat> list = user.getChats();

        list.removeIf(element -> element.getId().equals(id));

        user.setChats(list);

        userRepository.save(user);



        return "redirect:/chats";
    }

}
