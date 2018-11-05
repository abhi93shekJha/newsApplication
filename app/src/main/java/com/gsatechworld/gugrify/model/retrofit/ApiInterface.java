package com.gsatechworld.gugrify.model.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("gugrify_news/API/lang_city_list.php")
    Call<CityResponse> getAllCities(@Query("lang_id") String id);

    @GET("gugrify_news/API/language_list.php")
    Call<LanguageResponse> getAllLanguages();

    @GET("gugrify_news/API/latest_news.php")
    Call<LanguageResponse> getLatestNews(@Query("city") String city);

    @POST("gugrify_news/API/post_news.php")
    Call<ReporterPost> getLatestNews(@Body ReporterPost post);

    @GET("gugrify_news/API/get_audio.php")
    Call<GetMainAdvertisement> getMainAdvertisement();

    @GET("gugrify_news/API/get_ads.php")
    Call<CityWiseAdvertisement> getReporterAdvertisement(@Query("city") String city);

}
