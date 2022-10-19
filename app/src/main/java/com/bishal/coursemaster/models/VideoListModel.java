package com.bishal.coursemaster.models;

import java.util.List;

public class VideoListModel {
    private String topic;
    private List<String> subTopics;
    private List<String> links;
    private List<String> thumbs;

    public VideoListModel() {
    }

    public VideoListModel(String topic, List<String> subTopics, List<String> links, List<String> thumbs) {
        this.topic = topic;
        this.subTopics = subTopics;
        this.links = links;
        this.thumbs = thumbs;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<String> getSubTopics() {
        return subTopics;
    }

    public void setSubTopics(List<String> subTopics) {
        this.subTopics = subTopics;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public List<String> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<String> thumbs) {
        this.thumbs = thumbs;
    }
}
