package com.gsatechworld.gugrify.view.playlist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPlaylistsPojo {

    @SerializedName("result")
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }

    public class Result{
        @SerializedName("response")
        private String response;

        @SerializedName("user_id")
        private String user_id;

        @SerializedName("playlist_id")
        private String playlist_id;

        @SerializedName("playlist_name")
        private String playlist_name;

        @SerializedName("playlist_count")
        private String playlist_count;

        @SerializedName("playlist_image")
        private Object playlist_image;

        public String getResponse() {
            return response;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getPlaylist_id() {
            return playlist_id;
        }

        public String getPlaylist_name() {
            return playlist_name;
        }

        public String getPlaylist_count() {
            return playlist_count;
        }

        public Object getPlaylist_image() {
            return playlist_image;
        }

    }
}
