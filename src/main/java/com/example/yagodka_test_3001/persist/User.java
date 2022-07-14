package com.example.yagodka_test_3001.persist;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne
    private InfoUser infouser;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LikedUser> likedUserList;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_chats"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private List<Chat> chats;

    public void addChatsToUser(Chat chat){
        if (chats == null){
            chats = new ArrayList<>();
        }

        chats.add(chat);
    }

    public void deleteChat(long id){
        chats.remove(id-1);
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public InfoUser getInfouser() {
        return infouser;
    }

    public void setInfouser(InfoUser infouser) {
        this.infouser = infouser;
    }

    public List<LikedUser> getLikedUserList() {
        return likedUserList;
    }

    public void setLikedUserList(List<LikedUser> likedUserList) {
        this.likedUserList = likedUserList;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
}


