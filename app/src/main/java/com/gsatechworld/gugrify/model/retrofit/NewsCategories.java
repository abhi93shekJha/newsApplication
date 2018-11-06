package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsCategories {

    @SerializedName("category")
    @Expose
    private List<String> category = null;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}
