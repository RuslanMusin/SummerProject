package com.summer.itis.summerproject.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.summer.itis.summerproject.api.Card;

import java.util.List;

@IgnoreExtraProperties
public class User {

    private String id;

    private String email;

    private String username;

    private String photoUrl;

    private String desc;

    private String score;

    private List<Card> cards;

    private List<Test> tests;

    private String role;

    public User() {
    }

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
