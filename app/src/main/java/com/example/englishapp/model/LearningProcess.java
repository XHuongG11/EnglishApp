package com.example.englishapp.model;

import java.util.ArrayList;
import java.util.List;

public class LearningProcess {
    private List<TopicProgress> topicProgress;
    private User user;
    // constructor, getter, setter
    public LearningProcess()
    {
        topicProgress = new ArrayList<>();
    }
    public LearningProcess(Long id, List<TopicProgress> topicProgress, User user)
    {
        this.topicProgress = topicProgress;
        this.user = user;
    }
    //getters

    public List<TopicProgress> getTopicProgress() {
        return topicProgress;
    }

    public User getUser() {
        return user;
    }
    //setters

    public void setTopicProgress(List<TopicProgress> topicProgress) {
        this.topicProgress = topicProgress;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

