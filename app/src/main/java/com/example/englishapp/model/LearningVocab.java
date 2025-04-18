package com.example.englishapp.model;

public class LearningVocab {
    private Long id;
    private User user;
    private Word word;

    public LearningVocab(Word word, User user, Long id) {
        this.word = word;
        this.user = user;
        this.id = id;
    }

    public LearningVocab() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
