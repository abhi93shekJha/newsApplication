package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaylistPostPojo {

    String user_id,post_id,playlist_id,playlist_name;

    public PlaylistPostPojo(String user_id, String post_id, String playlist_id, String playlist_name) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.playlist_id = playlist_id;
        this.playlist_name = playlist_name;
    }

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("result")
    @Expose
    private Result result;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("playlist_id")
        @Expose
        private String playlistId;
        @SerializedName("playlist_name")
        @Expose
        private String playlistName;
        @SerializedName("post_id")
        @Expose
        private String postId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }
}
