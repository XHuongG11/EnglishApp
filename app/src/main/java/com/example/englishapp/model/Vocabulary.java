package com.example.englishapp.model;

import java.util.List;

public class Vocabulary {
    private Long id;
    private String Word;
    private String IPA;
    private String Meanings;
    private String Definitions;
    private String Examples;
    private String Type;
    private String Image_URL;

    // Reference
    private List<LearningVocab> nguoiDungDangHoc;

    // Constructor, getter, setter

    public Vocabulary() {
    }

    public Vocabulary(String Word) {
        this.Word = Word;
    }

    public Vocabulary(Long id, String Word, String IPA, String Meanings, String Definitions, String Examples, String Type, String Image_URL, List<LearningVocab> nguoiDungDangHoc) {
        this.id = id;
        this.Word = Word;
        this.IPA = IPA;
        this.Meanings = Meanings;
        this.Definitions = Definitions;
        this.Examples = Examples;
        this.Type = Type;
        this.Image_URL = Image_URL;
        this.nguoiDungDangHoc = nguoiDungDangHoc;
    }

    public Vocabulary(Long id, String Word, String IPA, String Meanings, String Definitions, String Examples, String Type, String Image_URL) {
        this.id = id;
        this.Word = Word;
        this.IPA = IPA;
        this.Meanings = Meanings;
        this.Definitions = Definitions;
        this.Examples = Examples;
        this.Type = Type;
        this.Image_URL = Image_URL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        this.Word = word;
    }

    public String getIPA() {
        return IPA;
    }

    public void setIPA(String IPA) {
        this.IPA = IPA;
    }

    public String getMeanings() {
        return Meanings;
    }

    public void setMeanings(String meanings) {
        this.Meanings = meanings;
    }

    public String getDefinitions() {
        return Definitions;
    }

    public void setDefinitions(String definitions) {
        this.Definitions = definitions;
    }

    public String getExamples() {
        return Examples;
    }

    public void setExamples(String examples) {
        this.Examples = examples;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getImage_URL() {
        return Image_URL;
    }

    public void setImage_URL(String image_URL) {
        this.Image_URL = image_URL;
    }

    public List<LearningVocab> getNguoiDungDangHoc() {
        return nguoiDungDangHoc;
    }

    public void setNguoiDungDangHoc(List<LearningVocab> nguoiDungDangHoc) {
        this.nguoiDungDangHoc = nguoiDungDangHoc;
    }
}