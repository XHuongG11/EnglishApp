package com.example.englishapp.model;

public class TopicProgress {
    private Long id;
    private Topic topic;
    private Practice currentPractice;

    // constructor, getter, setter
    public TopicProgress(){

    }
    public TopicProgress(Long id, Topic topic, Practice currentPractice) {
        this.id = id;
        this.topic = topic;
        this.currentPractice = currentPractice;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Practice getCurrentPractice() {
        return currentPractice;
    }

    public void setCurrentPractice(Practice currentPractice) {
        this.currentPractice = currentPractice;
    }
}

