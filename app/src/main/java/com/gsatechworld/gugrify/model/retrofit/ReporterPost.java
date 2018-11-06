package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.SerializedName;

public class ReporterPost {
    String reporter_id;
    String image;
    String selection;
    String news_title;
    String news_headline;
    String news_brief;
    String news_description;
    String youtube_link;
    String category;
    String language, city;
    String[] text_array, image_array;

    public ReporterPost(String reporter_id, String image, String selection, String news_title, String news_headline, String news_brief, String news_description, String youtube_link, String category, String language, String[] text_array, String[] image_array, String city) {
        this.reporter_id = reporter_id;
        this.image = image;
        this.selection = selection;
        this.text_array = text_array;
        this.image_array = image_array;
        this.news_title = news_title;
        this.news_headline = news_headline;
        this.news_brief = news_brief;
        this.news_description = news_description;
        this.youtube_link = youtube_link;
        this.category = category;
        this.language = language;
        this.city = city;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @SerializedName("response")
    String response;

}
