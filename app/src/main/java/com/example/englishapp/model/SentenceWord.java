package com.example.englishapp.model;

public class SentenceWord {
    private Long id;
    private Question sentence;
    private Word word;

    public SentenceWord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getSentence() {
        return sentence;
    }

    public void setSentence(Question sentence) {
        this.sentence = sentence;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
