package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPostsByPlaylistId {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result{
        @SerializedName("playlist_id")
        @Expose
        private String playlistId;
        @SerializedName("playlist_name")
        @Expose
        private String playlistName;
        @SerializedName("post_id")
        @Expose
        private String postId;
        @SerializedName("post_images")
        @Expose
        private String postImages;
        @SerializedName("post_headline")
        @Expose
        private String postHeadline;
        @SerializedName("post_title")
        @Expose
        private String postTitle;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("views")
        @Expose
        private String views;
        @SerializedName("likes")
        @Expose
        private Integer likes;

        private boolean clicked = false;

        public boolean isClicked(){
            return clicked;
        }

        public void setClicked(boolean value){
            clicked = value;
        }


        public String getPlaylistId() {
            return playlistId;
        }

        public void setPlaylistId(String playlistId) {
            this.playlistId = playlistId;
        }

        public String getPlaylistName() {
            return playlistName;
        }

        public void setPlaylistName(String playlistName) {
            this.playlistName = playlistName;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getPostImages() {
            return postImages;
        }

        public void setPostImages(String postImages) {
            this.postImages = postImages;
        }

        public String getPostHeadline() {
            return postHeadline;
        }

        public void setPostHeadline(String postHeadline) {
            this.postHeadline = postHeadline;
        }

        public String getPostTitle() {
            return postTitle;
        }

        public void setPostTitle(String postTitle) {
            this.postTitle = postTitle;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getViews() {
            return views;
        }

        public void setViews(String views) {
            this.views = views;
        }

        public Integer getLikes() {
            return likes;
        }

        public void setLikes(Integer likes) {
            this.likes = likes;
        }
    }
}
