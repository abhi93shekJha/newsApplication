package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.SerializedName;

public class ContactUsPojo {

    String name;
    String mobile_number;
    String email;
    String message;

    @SerializedName("response")
    private String response;

    public ContactUsPojo(String name, String mobile_number, String email, String message) {
        this.name = name;
        this.mobile_number = mobile_number;
        this.email = email;
        this.message = message;
    }

}
