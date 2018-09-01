package com.gsatechworld.gugrify.model;

public class NewsListHolder {
    public String getPostHeading() {
        return PostHeading;
    }

    public void setPostHeading(String postHeading) {
        PostHeading = postHeading;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String PostHeading;
    private String image;
    private String views;
    private String likes;
    private String description;

}
