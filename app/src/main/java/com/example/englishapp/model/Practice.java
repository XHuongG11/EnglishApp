package com.example.englishapp.model;

import java.util.ArrayList;
import java.util.List;

public class Practice {
    private EPracticeType type;

    private Topic topic;
    private List<Sentence> questions;

    // constructor, getter, setter

    public Practice() {
        questions = new ArrayList<>();
    }

    public Practice(EPracticeType type, Topic topic, List<Sentence> questions) {
        this.type = type;
        this.topic = topic;
        this.questions = questions;
    }
    //getters


    public EPracticeType getType() {
        return type;
    }

    public Topic getTopic() {
        return topic;
    }

    public List<Sentence> getQuestions() {
        return questions;
    }

    //setters


    public void setType(EPracticeType type) {
        this.type = type;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setQuestions(List<Sentence> questions) {
        this.questions = questions;
    }
}

