package com.example.englishapp.model;

import java.util.List;

public class Word {
    private Long id;
    private String noidung;
    private String nghia;
    private String phatAm;
    private String cauVidu;
    private String urlImg;

    //reference
    private List<LearningVocab> nguoiDungDangHoc;
    // constructor, getter, setter

    public Word() {
    }
    public Word(String noidung) {
        this.noidung = noidung;
    }
    public Word(Long id, String noidung, String nghia, String phatAm, String cauVidu, String urlImg, List<LearningVocab> nguoiDungDangHoc) {
        this.id = id;
        this.noidung = noidung;
        this.nghia = nghia;
        this.phatAm = phatAm;
        this.cauVidu = cauVidu;
        this.urlImg = urlImg;
        this.nguoiDungDangHoc = nguoiDungDangHoc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getNghia() {
        return nghia;
    }

    public void setNghia(String nghia) {
        this.nghia = nghia;
    }

    public String getPhatAm() {
        return phatAm;
    }

    public void setPhatAm(String phatAm) {
        this.phatAm = phatAm;
    }

    public String getCauVidu() {
        return cauVidu;
    }

    public void setCauVidu(String cauVidu) {
        this.cauVidu = cauVidu;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public List<LearningVocab> getNguoiDungDangHoc() {
        return nguoiDungDangHoc;
    }

    public void setNguoiDungDangHoc(List<LearningVocab> nguoiDungDangHoc) {
        this.nguoiDungDangHoc = nguoiDungDangHoc;
    }
}

