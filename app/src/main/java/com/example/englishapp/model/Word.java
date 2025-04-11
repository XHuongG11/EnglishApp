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
    private List<TuVungHocTap> nguoiDungDangHoc;
    private List<SentenceWord> sentenceWords;
    // constructor, getter, setter

    public Word() {
    }

    public Word(Long id, String noidung, String nghia, String phatAm, String cauVidu, String urlImg, List<TuVungHocTap> nguoiDungDangHoc, List<SentenceWord> sentenceWords) {
        this.id = id;
        this.noidung = noidung;
        this.nghia = nghia;
        this.phatAm = phatAm;
        this.cauVidu = cauVidu;
        this.urlImg = urlImg;
        this.nguoiDungDangHoc = nguoiDungDangHoc;
        this.sentenceWords = sentenceWords;
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

    public List<TuVungHocTap> getNguoiDungDangHoc() {
        return nguoiDungDangHoc;
    }

    public void setNguoiDungDangHoc(List<TuVungHocTap> nguoiDungDangHoc) {
        this.nguoiDungDangHoc = nguoiDungDangHoc;
    }

    public List<SentenceWord> getSentenceWords() {
        return sentenceWords;
    }

    public void setSentenceWords(List<SentenceWord> sentenceWords) {
        this.sentenceWords = sentenceWords;
    }
}

