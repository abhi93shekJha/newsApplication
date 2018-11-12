package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetScrollNewsAndBNPojo {
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
        @SerializedName("scroll news")
        @Expose
        private List<ScrollNews> scrollNews = null;
        @SerializedName("top news")
        @Expose
        private List<TopNews> topNews = null;

        public List<ScrollNews> getScrollNews() {
            return scrollNews;
        }

        public void setScrollNews(List<ScrollNews> scrollNews) {
            this.scrollNews = scrollNews;
        }

        public List<TopNews> getTopNews() {
            return topNews;
        }

        public void setTopNews(List<TopNews> topNews) {
            this.topNews = topNews;
        }
    }

    public class ScrollNews{
        @SerializedName("text1")
        @Expose
        private String text1;
        @SerializedName("text2")
        @Expose
        private String text2;
        @SerializedName("text3")
        @Expose
        private String text3;

        public String getText1() {
            return text1;
        }

        public void setText1(String text1) {
            this.text1 = text1;
        }

        public String getText2() {
            return text2;
        }

        public void setText2(String text2) {
            this.text2 = text2;
        }

        public String getText3() {
            return text3;
        }

        public void setText3(String text3) {
            this.text3 = text3;
        }
    }

    public class TopNews{
        @SerializedName("text1")
        @Expose
        private String text1;
        @SerializedName("text2")
        @Expose
        private String text2;
        @SerializedName("text3")
        @Expose
        private String text3;

        public String getText1() {
            return text1;
        }

        public void setText1(String text1) {
            this.text1 = text1;
        }

        public String getText2() {
            return text2;
        }

        public void setText2(String text2) {
            this.text2 = text2;
        }

        public String getText3() {
            return text3;
        }

        public void setText3(String text3) {
            this.text3 = text3;
        }
    }

}
