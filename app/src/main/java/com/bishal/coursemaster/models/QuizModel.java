package com.bishal.coursemaster.models;

public class QuizModel {
    private String name;
    private String background;
    private String ratting;
    private String uid;

    public QuizModel() {

    }

    public QuizModel(String name, String background, String ratting, String uid) {
        this.name = name;
        this.background = background;
        this.ratting = ratting;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getRatting() {
        return ratting;
    }

    public void setRatting(String ratting) {
        this.ratting = ratting;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
