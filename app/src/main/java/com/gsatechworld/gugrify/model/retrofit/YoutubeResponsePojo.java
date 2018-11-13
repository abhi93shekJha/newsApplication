package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YoutubeResponsePojo {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("response")
        @Expose
        private String response;
        @SerializedName("youtube_link")
        @Expose
        private String youtubeLink;
        @SerializedName("news_description")
        @Expose
        private String news_description;
        @SerializedName("news_title")
        @Expose
        private String newsTitle;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getYoutubeLink() {
            return youtubeLink;
        }

        public void setYoutubeLink(String youtubeLink) {
            this.youtubeLink = youtubeLink;
        }

        public String getNews_description() {
            return news_description;
        }

        public void setNews_description(String newsHeadline) {
            this.news_description = newsHeadline;
        }

        public String getNewsTitle() {
            return newsTitle;
        }

        public void setNewsTitle(String newsTitle) {
            this.newsTitle = newsTitle;
        }

    }
}
