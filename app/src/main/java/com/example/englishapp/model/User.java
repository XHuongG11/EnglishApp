package com.example.englishapp.model;

import java.util.List;

public class User {
    private Long id;
    private String username;
    private String email;
    private String password;

    //reference
    private List<TuVungHocTap> tuvunghoctap;
    private LearningProcess tiendohoctap;

    public User(Long id, String username, String email, String password, List<TuVungHocTap> tuvunghoctap, LearningProcess tiendohoctap) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.tuvunghoctap = tuvunghoctap;
        this.tiendohoctap = tiendohoctap;
    }
    public User(){

    }
    //getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TuVungHocTap> getTuvunghoctap() {
        return tuvunghoctap;
    }

    public void setTuvunghoctap(List<TuVungHocTap> tuvunghoctap) {
        this.tuvunghoctap = tuvunghoctap;
    }

    public LearningProcess getTiendohoctap() {
        return tiendohoctap;
    }

    public void setTiendohoctap(LearningProcess tiendohoctap) {
        this.tiendohoctap = tiendohoctap;
    }

    //setters

}
