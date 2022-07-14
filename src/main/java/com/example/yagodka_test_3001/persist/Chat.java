package com.example.yagodka_test_3001.persist;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String who;

    private String whoUsername;

    private String whomUsername;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "chat"
    )
    private List<Message> messages = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_chats"
            , joinColumns = @JoinColumn(name = "chat_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    public void addUserToChat(User user){
        if (users == null){
            users = new ArrayList<>();
        }

        users.add(user);
    }

    public void addMessageToChat(Message message){
        if (messages == null){
            users = new ArrayList<>();
        }

        messages.add(message);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getWhoUsername() {
        return whoUsername;
    }

    public void setWhoUsername(String whoUsername) {
        this.whoUsername = whoUsername;
    }

    public String getWhomUsername() {
        return whomUsername;
    }

    public void setWhomUsername(String whomUsername) {
        this.whomUsername = whomUsername;
    }
}
