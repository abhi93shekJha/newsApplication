package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityWiseAdvertisement {

    @SerializedName("result")
    List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @SerializedName("response")
    private String response;

    public class Result{
        @SerializedName("id")
        private String id;

        @SerializedName("city")
        private String city;

        public String getId() {
            return id;
        }

        public String getCity() {
            return city;
        }

        public String getImage() {
            return image;
        }

        public String getText1() {
            return text1;
        }

        public String getText2() {
            return text2;
        }

        public String getUrl() {
            return url;
        }

        public String getAudio() {
            return audio;
        }

        public String getReporter_id() {
            return reporter_id;
        }

        @SerializedName("image")
        private String image;

        @SerializedName("text1")
        private String text1;

        @SerializedName("text2")
        private String text2;

        @SerializedName("url")
        private String url;

        @SerializedName("audio")
        private String audio;

        @SerializedName("reporter_id")
        private String reporter_id;
    }

}
