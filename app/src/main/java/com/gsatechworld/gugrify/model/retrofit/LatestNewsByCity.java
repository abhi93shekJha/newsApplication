package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LatestNewsByCity {

    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }

    public List<Home> getHome() {
        return home;
    }

    public List<Result> getResult() {
        return result;
    }

    @SerializedName("home")
    private List<Home> home;

    @SerializedName("result")
    private List<Result> result;

    public class Result{
        @SerializedName("post_id")
        private String post_id;

        @SerializedName("category")
        private String category;

        @SerializedName("image")
        private String image;

        @SerializedName("news_headline")
        private String news_headline;

        public String getPost_id() {
            return post_id;
        }

        public String getCategory() {
            return category;
        }

        public String getImage() {
            return image;
        }

        public String getNews_headline() {
            return news_headline;
        }
    }

    public class Home{
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }

        public String getImage() {
            return image;
        }

        @SerializedName("image")
        private String image;
    }
}
