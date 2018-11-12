package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserRegistrationPojo {
    String name;
    String user_id;
    String email;
    String mobile_number;
    String user_city;
    String user_image;
    String password;

    public UserRegistrationPojo(String name, String user_id, String email, String mobile_number, String user_city, String user_image, String password) {
        this.name = name;
        this.user_id = user_id;
        this.email = email;
        this.mobile_number = mobile_number;
        this.user_city = user_city;
        this.user_image = user_image;
        this.password = password;
    }

    public Object getResult() {
        return result;
    }

    @SerializedName("result")
    @Expose

    private Object result;


}
