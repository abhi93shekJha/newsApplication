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

    public class Result{
        @SerializedName("response")
        private String response;

        @SerializedName("reporter_id")
        private String id;

        @SerializedName("city")
        private String city;

        @SerializedName("ad_image")
        private String image;

        @SerializedName("ad_text1")
        private String text1;

        @SerializedName("ad_text2")
        private String text2;

        @SerializedName("ad_url")
        private String url;

        @SerializedName("ad_audio")
        private String audio;

        public String getResponse() {
            return response;
        }

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

    }

}
