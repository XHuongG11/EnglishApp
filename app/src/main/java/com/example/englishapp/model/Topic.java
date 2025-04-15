package com.example.englishapp.model;

import java.util.List;

public class Topic {
    private List<Practice> practices;

    // constructor, getter, setter
        //constructor

    public Topic() {
    }


    public List<Practice> getPractices() {
        return practices;
    }

    public void setPractices(List<Practice> practices) {
        this.practices = practices;
    }
}

