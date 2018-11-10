package com.gsatechworld.gugrify.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentsPostPojo {

    String post_id;
    String user_id;
    String comments;

    public CommentsPostPojo(String post_id, String user_id, String comments) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.comments = comments;
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

        @SerializedName("post_id")
        @Expose
        private String postId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("comments")
        @Expose
        private String comments;

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

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

    }
}
