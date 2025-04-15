package com.example.englishapp.model;

import java.util.List;

public class Sentence {
    private String content;
    private List<SentenceWord> answers;
    private int correctAnswer; // 0 1 2 4 cua list answers

    private Practice practice; // cau nay thuoc bai thuc hanh nao

    // constructor, getter, setter
    public Sentence(){

    }
    public Sentence(String content, List<SentenceWord> answers, int correctAnswer, Practice practice) {
        this.content = content;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.practice = practice;
    }
        //getters

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<SentenceWord> getAnswers() {
        return answers;
    }

    public void setAnswers(List<SentenceWord> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Practice getPractice() {
        return practice;
    }

    public void setPractice(Practice practice) {
        this.practice = practice;
    }

    //setters
}
