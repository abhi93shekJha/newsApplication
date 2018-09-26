package com.gsatechworld.gugrify.view.playlist;

import java.util.ArrayList;

public class PlayListModel {
    String image;
    String news_headlines;
    String views;
    String likes;
    ArrayList<String> comments;
    ArrayList<String> texts;
    boolean isClicked;

    public PlayListModel(String image, String news_headlines, String views, String likes,
                         ArrayList<String> comments, ArrayList<String> texts, boolean isClicked) {
        this.image = image;
        this.news_headlines = news_headlines;
        this.views = views;
        this.likes = likes;
        this.comments = comments;
        this.texts = texts;
        this.isClicked = isClicked;
    }

    public String getImage() {
        return image;
    }

    public String getNews_headlines() {
        return news_headlines;
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

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
}
