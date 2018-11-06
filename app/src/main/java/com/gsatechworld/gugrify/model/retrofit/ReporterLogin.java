package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.SerializedName;

public class ReporterLogin {

    @SerializedName("message")
    String message;
    @SerializedName("reporter_id")
    String reporter_id;
    @SerializedName("reporter_name")
    String reporter_name;
    @SerializedName("reporter_place")
    String reporter_place;
    @SerializedName("language")
    String language;
    @SerializedName("reporter_pic")
    String reporter_pic;
    @SerializedName("total_posts_count")
    String total_posts_count;
    @SerializedName("total_ads_count")
    String total_ads_count;

    public String getMessage() {
        return message;
    }

    public String getReporter_id() {
        return reporter_id;
    }

    public String getReporter_name() {
        return reporter_name;
    }

    public String getReporter_place() {
        return reporter_place;
    }

    public String getLanguage() {
        return language;
    }

    public String getReporter_pic() {
        return reporter_pic;
    }

    public String getTotal_posts_count() {
        return total_posts_count;
    }

    public String getTotal_ads_count() {
        return total_ads_count;
    }
}
