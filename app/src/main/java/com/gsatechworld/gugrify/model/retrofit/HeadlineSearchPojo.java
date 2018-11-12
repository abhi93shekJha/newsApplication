package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeadlineSearchPojo {

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

        @SerializedName("post_id")
        @Expose
        private String postId;
        @SerializedName("news_headline")
        @Expose
        private String newsHeadline;

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getNewsHeadline() {
            return newsHeadline;
        }

        public void setNewsHeadline(String newsHeadline) {
            this.newsHeadline = newsHeadline;
        }

    }

}
