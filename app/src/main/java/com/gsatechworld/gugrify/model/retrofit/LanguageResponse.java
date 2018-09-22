package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LanguageResponse {

    public List<LanguageResponse.language> getResult() {
        return result;
    }

    public void setResult(List<LanguageResponse.language> result) {
        this.result = result;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @SerializedName("result")
    private List<LanguageResponse.language> result;

    @SerializedName("response")
    private String response;

    public class language{

        @SerializedName("languages")
        private String language;

        @SerializedName("id")
        private String languageId;

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getLanguageId() {
            return languageId;
        }

        public void setLanguageId(String languageId) {
            this.languageId = languageId;
        }

        public language(String language, String languageId) {
            this.language = language;
            this.languageId = languageId;
        }
    }

}
