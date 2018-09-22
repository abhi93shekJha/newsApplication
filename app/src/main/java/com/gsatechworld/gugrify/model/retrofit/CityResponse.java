package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityResponse {

    public CityResponse(List<city> result, String response) {
        this.result = result;
        this.response = response;
    }

    public List<city> getResult() {
        return result;
    }

    public void setResult(List<city> result) {
        this.result = result;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @SerializedName("result")
    private List<city> result;

    @SerializedName("response")
    private String response;

    public class city{

        @SerializedName("language")
        private String language;

        @SerializedName("lang_id")
        private String lang_id;

        @SerializedName("cities")
        private String cities;

        @SerializedName("images")
        private String images;

        @SerializedName("id")
        private String id;

        public city(String language, String lang_id, String cities, String images, String id) {
            this.language = language;
            this.lang_id = lang_id;
            this.cities = cities;
            this.images = images;
            this.id = id;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getLang_id() {
            return lang_id;
        }

        public void setLang_id(String lang_id) {
            this.lang_id = lang_id;
        }

        public String getCities() {
            return cities;
        }

        public void setCities(String cities) {
            this.cities = cities;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
