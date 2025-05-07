package com.example.englishapp.model;

public class FlyingWord {
    public float x, y;
    public String word;
    public float speedX;

    public FlyingWord(String word, float x, float y, float speedX) {
        this.word = word;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
    }

    public void update() {
        x += speedX;
        if (x > 1080 || x < -200) {
            speedX = -speedX; // quay đầu
        }
    }
}

