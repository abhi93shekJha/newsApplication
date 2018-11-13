package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.SerializedName;

public class LikePojo {

    String post_id;

    public LikePojo(String post_id, String user_id) {
        this.post_id = post_id;
        this.user_id = user_id;
    }

    String user_id;

    public String getResponse() {
        return response;
    }

    public Result getResult() {
        return result;
    }

    @SerializedName("response")

    private String response;

    @SerializedName("result")
    private Result result;

    public class Result{

        @SerializedName("post_id")
        private String post_id;

        @SerializedName("user_id")
        private Result user_id;

        public String getPost_id() {
            return post_id;
        }

        public Result getUser_id() {
            return user_id;
        }

        public Integer getLikes() {
            return likes;
        }

        @SerializedName("likes")
        private Integer likes;

    }

}
