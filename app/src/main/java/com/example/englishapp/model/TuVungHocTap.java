package com.example.englishapp.model;

public class TuVungHocTap {
    private User user;
    private Word word;

    public TuVungHocTap(Word word, User user) {
        this.word = word;
        this.user = user;
    }

    public TuVungHocTap() {
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
