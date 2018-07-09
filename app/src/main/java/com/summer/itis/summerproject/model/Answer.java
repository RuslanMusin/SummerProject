package com.summer.itis.summerproject.model;

public class Answer {

    private String text;

    private boolean isRight;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }
}
