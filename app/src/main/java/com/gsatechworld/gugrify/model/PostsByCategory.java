package com.gsatechworld.gugrify.model;

import java.util.ArrayList;

public class PostsByCategory {
    String image;
    String news_headlines;
    String news_description;
    String views;
    String likes;
    ArrayList<String> comments;
    ArrayList<String> texts;

    public PostsByCategory(String image, String news_headlines, String news_description, String views, String likes,
                           ArrayList<String> comments, ArrayList<String> texts) {
        this.image = image;
        this.news_headlines = news_headlines;
        this.news_description = news_description;
        this.views = views;
        this.likes = likes;
        this.comments = comments;
        this.texts = texts;
    }

    public String getImage() {
        return image;
    }

    public String getNews_headlines() {
        return news_headlines;
    }

    public String getNews_description() {
        return news_description;
    }

    public String getViews() {
        return views;
    }

    public String getLikes() {
        return likes;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public ArrayList<String> getTexts() {
        return texts;
    }

}
