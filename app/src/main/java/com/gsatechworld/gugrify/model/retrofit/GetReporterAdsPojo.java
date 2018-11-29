package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetReporterAdsPojo {

    @SerializedName("result")
    @Expose
    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public class Result{

        @SerializedName("response")
        @Expose
        private String response;

        @SerializedName("city")
        @Expose
        private String city;

        @SerializedName("image")
        @Expose
        private String image;

        @SerializedName("text1")
        @Expose
        private String text1;

        @SerializedName("text2")
        @Expose
        private String text2;

        @SerializedName("url")
        @Expose
        private String url;

        @SerializedName("audio")
        @Expose
        private String audio;

        @SerializedName("date")
        @Expose
        private String date;

        public String getResponse() {
            return response;
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

        public String getDate() {
            return date;
        }
    }
}
