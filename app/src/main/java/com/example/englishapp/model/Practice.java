package com.example.englishapp.model;

import java.util.ArrayList;
import java.util.List;

public class Practice {
    private Long id;
    private EPracticeType type;

    private Topic topic;
    private List<Question> questions;

    // constructor, getter, setter

    public Practice() {
        questions = new ArrayList<>();
    }

    public Practice(Long id, EPracticeType type, Topic topic, List<Question> questions) {
        this.id = id;
        this.type = type;
        this.topic = topic;
        this.questions = questions;
    }
    //getters

    public Long getId() {
        return id;
    }

    public EPracticeType getType() {
        return type;
    }

    public Topic getTopic() {
        return topic;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    //setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(EPracticeType type) {
        this.type = type;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

