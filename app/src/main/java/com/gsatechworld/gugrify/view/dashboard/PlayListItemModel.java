package com.gsatechworld.gugrify.view.dashboard;

public class PlayListItemModel {
    private String name, url, description;
    private int image;

    public PlayListItemModel(String name, String url, String description, int image) {
        this.name = name;
        this.url = url;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
