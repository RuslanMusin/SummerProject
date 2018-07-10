package com.summer.itis.summerproject.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.summer.itis.summerproject.api.Card;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Test {

    private String id;

    private String title;

    private String desc;

    private String authorId;

    private String authorName;

    @Exclude
    private List<Question> questions = new ArrayList<>();

    @Exclude
    private List<Comment> comments = new ArrayList<>();

    @Exclude
    private Card card;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Card getCard() {
        return card;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
