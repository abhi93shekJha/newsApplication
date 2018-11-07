package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryPosts {
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
        @SerializedName("post_id")
        @Expose
        private String postId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("news_headline")
        @Expose
        private String newsHeadline;
        @SerializedName("news_description")
        @Expose
        private String newsDescription;
        @SerializedName("views")
        @Expose
        private String views;
        @SerializedName("likes")
        @Expose
        private String likes;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNewsHeadline() {
            return newsHeadline;
        }

        public void setNewsHeadline(String newsHeadline) {
            this.newsHeadline = newsHeadline;
        }

        public String getNewsDescription() {
            return newsDescription;
        }

        public void setNewsDescription(String newsDescription) {
            this.newsDescription = newsDescription;
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

    }

}


