package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ActivePostsPojo {


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
            @SerializedName("news_title")
            @Expose
            private String newsTitle;
            @SerializedName("category")
            @Expose
            private String category;
            @SerializedName("reporter_location")
            @Expose
            private String reporterLocation;
            @SerializedName("time_of_post")
            @Expose
            private String timeOfPost;
            @SerializedName("reporter_image")
            @Expose
            private String reporterImage;
            @SerializedName("likes")
            @Expose
            private Integer likes;
            @SerializedName("views")
            @Expose
            private String views;
            @SerializedName("published_date")
            @Expose
            private String published_date;

            public String getPublished_date() {
                return published_date;
            }

            public void setPublished_date(String published_date) {
                this.published_date = published_date;
            }

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

            public String getNewsTitle() {
                return newsTitle;
            }

            public void setNewsTitle(String newsTitle) {
                this.newsTitle = newsTitle;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getReporterLocation() {
                return reporterLocation;
            }

            public void setReporterLocation(String reporterLocation) {
                this.reporterLocation = reporterLocation;
            }

            public String getTimeOfPost() {
                return timeOfPost;
            }

            public void setTimeOfPost(String timeOfPost) {
                this.timeOfPost = timeOfPost;
            }

            public String getReporterImage() {
                return reporterImage;
            }

            public void setReporterImage(String reporterImage) {
                this.reporterImage = reporterImage;
            }

            public Integer getLikes() {
                return likes;
            }

            public void setLikes(Integer likes) {
                this.likes = likes;
            }

            public String getViews() {
                return views;
            }

            public void setViews(String views) {
                this.views = views;
            }
        }
    }

