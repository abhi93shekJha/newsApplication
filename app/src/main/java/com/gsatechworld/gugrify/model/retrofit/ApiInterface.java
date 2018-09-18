package com.gsatechworld.gugrify.model.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("cities_list.php")
    Call<City> getAllCities();

}
