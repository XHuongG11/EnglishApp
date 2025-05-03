package com.example.englishapp.model;
//card của phần listen and repeat
public class CardItem {
    private Word word;
    private int textColor;
    private boolean showEye;

    // Constructor
    public CardItem(Word word, boolean showEye) {
        this.word = word;
        this.showEye = showEye;
    }

    // Getters
    public Word getWord() {
        return word;
    }

    public boolean isShowEye() {
        return showEye;
    }
}
