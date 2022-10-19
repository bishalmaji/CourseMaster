package com.bishal.coursemaster.models;

public class EbookModel {
    private String title;
    private String filename;
    private String detail;
    private String icon;
    private String link;
    private String ratting;

    public EbookModel() {
    }

    public EbookModel(String title, String filename, String detail, String icon, String link, String ratting) {
        this.title = title;
        this.filename = filename;
        this.detail = detail;
        this.icon = icon;
        this.link = link;

        this.ratting = ratting;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRatting() {
        return ratting;
    }

    public void setRatting(String ratting) {
        this.ratting = ratting;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
