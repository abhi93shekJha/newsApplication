package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.SerializedName;

public class RegistrationPostPojo {

    String name;

    public RegistrationPostPojo(String name, String user_id, String email, String mobile_number, String user_city, String user_image, String password) {
        this.name = name;
        this.user_id = user_id;
        this.email = email;
        this.mobile_number = mobile_number;
        this.user_city = user_city;
        this.user_image = user_image;
        this.password = password;
    }

    String user_id;
    String email;
    String mobile_number;
    String user_city;
    String user_image;
    String password;

    @SerializedName("response")
    String response;
}
