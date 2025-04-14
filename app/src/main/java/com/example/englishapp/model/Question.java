package com.example.englishapp.model;

import java.util.List;

public class Question {
    private Long id;
    private String content; // he wanst tea (câu hỏi)
    private String meaning; // nghĩa tiếng việt nếu cần
    private Word correctAnswer; // đáp án đúng
    private List<Word> answers; // ds các đáp án

    private Practice practice; // cau nay thuoc bai thuc hanh nao

    // constructor, getter, setter
    public Question(){

    }

    public Question(Long id, String content, String meaning, Word correctAnswer, List<Word> answers, Practice practice) {
        this.id = id;
        this.content = content;
        this.meaning = meaning;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.practice = practice;
    }

    public Question(Long id, String content, String meaning, Word correctAnswer, List<Word> answers) {
        this.id = id;
        this.content = content;
        this.meaning = meaning;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public List<Word> getErrorAnswers() {
        return answers;
    }

    public void setErrorAnswers(List<Word> answers) {
        this.answers = answers;
    }

    public Word getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Word correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Practice getPractice() {
        return practice;
    }

    public void setPractice(Practice practice) {
        this.practice = practice;
    }
}
