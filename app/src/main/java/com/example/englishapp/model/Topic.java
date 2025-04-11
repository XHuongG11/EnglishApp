package com.example.englishapp.model;

import java.util.List;

public class Topic {
    private Long id;
    private List<Practice> practices;

    // constructor, getter, setter
        //constructor

    public Topic() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Practice> getPractices() {
        return practices;
    }

    public void setPractices(List<Practice> practices) {
        this.practices = practices;
    }
}

