package com.bishal.coursemaster.models;

public class CourseModel {
    private String icon;
    private String name;
    private String background;
    private String rupees;
    private String ratting;
    private String link;
    private String uid;
    public CourseModel() {
    }

    public CourseModel(String icon, String name, String background, String rupees, String ratting, String link, String uid) {
        this.icon = icon;
        this.name = name;
        this.background = background;
        this.rupees = rupees;
        this.ratting = ratting;
        this.link = link;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public String getRupees() {
        return rupees;
    }

    public void setRupees(String rupees) {
        this.rupees = rupees;
    }

    public String getRatting() {
        return ratting;
    }

    public void setRatting(String ratting) {
        this.ratting = ratting;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
