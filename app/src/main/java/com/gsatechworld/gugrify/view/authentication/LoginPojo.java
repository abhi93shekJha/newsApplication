package com.gsatechworld.gugrify.view.authentication;

import com.google.gson.annotations.SerializedName;

public class LoginPojo {

    public Result getResult() {
        return result;
    }

    @SerializedName("result")

    Result result;

    public class Result {
        @SerializedName("response")
        String response;
        @SerializedName("message")
        String message;

        public String getResponse() {
            return response;
        }

        public String getMessage() {
            return message;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public String getUser_image() {
            return user_image;
        }

        public String getUser_city() {
            return user_city;
        }

        @SerializedName("user_id")

        String user_id;
        @SerializedName("name")
        String name;
        @SerializedName("email")
        String email;
        @SerializedName("mobile_number")
        String mobileNumber;
        @SerializedName("user_image")
        String user_image;
        @SerializedName("user_city")
        String user_city;
    }
}
