package com.example.englishapp.model;

public class SentenceWord {
    private Sentence sentence;
    private Word word;

    public SentenceWord() {
    }


    public Sentence getSentence() {
        return sentence;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
