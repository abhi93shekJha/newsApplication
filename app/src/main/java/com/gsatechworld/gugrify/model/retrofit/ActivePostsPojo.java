package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ActivePostsPojo {

    public ActivePostsPojo(){}

    @SerializedName("result")
    private List<Result> results;

    @SerializedName("response")
    private String response;


    public List<Result> getResults() {
        return results;
    }

    public class Result{

        @SerializedName("response")
        private String response;

        @SerializedName("post_id")
        private String post_id;

        @SerializedName("image")
        private String image;

        @SerializedName("news_headline")
        private String news_headline;

        @SerializedName("news_title")
        private String news_title;

        @SerializedName("category")
        private String category;

        @SerializedName("reporter_location")
        private String reporter_location;

        @SerializedName("time_of_post")
        private String time_of_post;

        @SerializedName("reporter_image")
        private String reporter_image;

        @SerializedName("likes")
        private int likes;

        @SerializedName("views")
        private String views;

        public String getResponse() {
            return response;
        }

        public String getPost_id() {
            return post_id;
        }

        public String getImage() {
            return image;
        }

        public String getNews_headline() {
            return news_headline;
        }

        public String getNews_title() {
            return news_title;
        }

        public String getCategory() {
            return category;
        }

        public String getReporter_location() {
            return reporter_location;
        }

        public String getTime_of_post() {
            return time_of_post;
        }

        public String getReporter_image() {
            return reporter_image;
        }

        public int getLikes() {
            return likes;
        }

        public String getViews() {
            return views;
        }
    }
}
