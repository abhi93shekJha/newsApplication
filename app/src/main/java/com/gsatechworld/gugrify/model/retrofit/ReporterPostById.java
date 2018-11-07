package com.gsatechworld.gugrify.model.retrofit;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReporterPostById {


    @SerializedName("result")
    @Expose
    private List<Result> result = null;


    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result{
        @SerializedName("response")
        @Expose
        private String response;
        @SerializedName("post_id")
        @Expose
        private String postId;
        @SerializedName("post_image")
        @Expose
        private String postImage;
        @SerializedName("post_heading")
        @Expose
        private String postHeading;
        @SerializedName("post_date")
        @Expose
        private String postDate;
        @SerializedName("post_time")
        @Expose
        private String postTime;
        @SerializedName("views")
        @Expose
        private String views;

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

        public String getPostImage() {
            return postImage;
        }

        public void setPostImage(String postImage) {
            this.postImage = postImage;
        }

        public String getPostHeading() {
            return postHeading;
        }

        public void setPostHeading(String postHeading) {
            this.postHeading = postHeading;
        }

        public String getPostDate() {
            return postDate;
        }

        public void setPostDate(String postDate) {
            this.postDate = postDate;
        }

        public String getPostTime() {
            return postTime;
        }

        public void setPostTime(String postTime) {
            this.postTime = postTime;
        }

        public String getViews() {
            return views;
        }


    }

}