package com.example.englishapp.model;

public class TopicProgress {
    private Topic topic;
    private Practice currentPractice;

    // constructor, getter, setter
    public TopicProgress(){

    }
    public TopicProgress(Topic topic, Practice currentPractice) {
        this.topic = topic;
        this.currentPractice = currentPractice;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }


    public Practice getCurrentPractice() {
        return currentPractice;
    }

    public void setCurrentPractice(Practice currentPractice) {
        this.currentPractice = currentPractice;
    }
}

