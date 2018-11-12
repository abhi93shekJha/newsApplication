package com.gsatechworld.gugrify.view.playlist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreatePlayListPojo {

    String playlist_name, user_id;

    public CreatePlayListPojo(String playlist_name, String user_id) {
        this.playlist_name = playlist_name;
        this.user_id = user_id;
    }

    @SerializedName("result")
    private Result results = null;


    public Result getResults() {
        return results;
    }

    public class Result{
        @SerializedName("playlist_name")
        private String playlist_name;

        @SerializedName("user_id")

        private String user_id;

        public String getPlaylist_name() {
            return playlist_name;
        }

        public String getUser_id() {
            return user_id;
        }

    }

}
