package com.example.englishapp.model;

import com.example.englishapp.model.Practice;

import java.util.List;

public class Topic {
    private String numberTopic;
    private String name;
    private List<Practice> practices;
    private int backgroundResource; // Thêm thuộc tính để lưu ID tài nguyên background

    public Topic() {
    }

    public Topic(String numberTopic, String name, List<Practice> practices, int backgroundResource) {
        this.numberTopic = numberTopic;
        this.name = name;
        this.practices = practices;
        this.backgroundResource = backgroundResource;
    }

    public String getNumberTopic() {
        return numberTopic;
    }

    public String getName() {
        return name;
    }

    public List<Practice> getPractices() {
        return practices;
    }

    public void setPractices(List<Practice> practices) {
        this.practices = practices;
    }

    public int getBackgroundResource() {
        return backgroundResource;
    }
}