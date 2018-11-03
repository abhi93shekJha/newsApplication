package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.SerializedName;

public class GetMainAdvertisement {
    @SerializedName("response")
    public String response;

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

    @SerializedName("result")
    Result result;

    public class Result {
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAudio() {
            return audio;
        }

        public void setAudio(String audio) {
            this.audio = audio;
        }

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @SerializedName("id")
        String id;

        @SerializedName("audio")
        String audio;

        @SerializedName("text1")
        String text1;

        @SerializedName("text2")
        String text2;

        @SerializedName("image")
        String image;

        @SerializedName("url")
        String url;
    }
}
