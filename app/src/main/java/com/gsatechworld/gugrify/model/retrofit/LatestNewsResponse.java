package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LatestNewsResponse {

    @SerializedName("result")
    private List<LatestNewsResponse.news> result;

    @SerializedName("response")
    private String response;

    public List<news> getResult() {
        return result;
    }

    public void setResult(List<news> result) {
        this.result = result;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LatestNewsResponse(List<news> result, String response) {
        this.result = result;
        this.response = response;
    }

    public class news {

        @SerializedName("id")
        private String newsId;

        @SerializedName("image")
        private String image;

        @SerializedName("news_headline")
        private String headline;

        public news(String newsId, String image, String headline) {
            this.newsId = newsId;
            this.image = image;
            this.headline = headline;
        }
    }
}
