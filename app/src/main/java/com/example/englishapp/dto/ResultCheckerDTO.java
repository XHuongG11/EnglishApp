package com.example.englishapp.dto;

public class ResultCheckerDTO {
    private String targetText;
    private String spokenText;
    private int accuracyPercentage;
    private String formattedFeedback;

    // Full constructor
    public ResultCheckerDTO(String targetText, String spokenText, int accuracyPercentage, String formattedFeedback) {
        this.targetText = targetText;
        this.spokenText = spokenText;
        this.accuracyPercentage = accuracyPercentage;
        this.formattedFeedback = formattedFeedback;
    }

    // Getters and Setters
    public String getTargetText() {
        return targetText;
    }

    public void setTargetText(String targetText) {
        this.targetText = targetText;
    }

    public String getSpokenText() {
        return spokenText;
    }

    public void setSpokenText(String spokenText) {
        this.spokenText = spokenText;
    }

    public int getAccuracyPercentage() {
        return accuracyPercentage;
    }

    public void setAccuracyPercentage(int accuracyPercentage) {
        this.accuracyPercentage = accuracyPercentage;
    }

    public String getFormattedFeedback() {
        return formattedFeedback;
    }

    public void setFormattedFeedback(String formattedFeedback) {
        this.formattedFeedback = formattedFeedback;
    }
}
