package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikePojo {

    String post_id;

    String user_id;

    public LikePojo(String post_id, String user_id) {
        this.post_id = post_id;
        this.user_id = user_id;
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

    public class Result{

        @SerializedName("post_id")
        private String post_id;

        @SerializedName("user_id")
        private String user_id;

        @SerializedName("likes")
        private Integer likes;

        public String getPost_id() {
            return post_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public Integer getLikes() {
            return likes;
        }


    }

}
