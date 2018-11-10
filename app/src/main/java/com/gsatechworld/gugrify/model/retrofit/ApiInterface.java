package com.gsatechworld.gugrify.model.retrofit;

import com.gsatechworld.gugrify.model.CommentsPostPojo;
import com.gsatechworld.gugrify.model.PostsByCategory;
import com.gsatechworld.gugrify.view.authentication.LoginPojo;

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
    Call<ReporterPost> postReporterNews(@Body ReporterPost post);

    @GET("gugrify_news/API/get_audio.php")
    Call<GetMainAdvertisement> getMainAdvertisement();

    @GET("gugrify_news/API/get_ads.php")
    Call<CityWiseAdvertisement> getReporterAdvertisement(@Query("city") String city);

    @GET("gugrify_news/API/reporter_login")
    Call<ReporterLogin> reporterLogin(@Query("username") String username, @Query("password") String password, @Query("role") String role);

    @GET("gugrify_news/API/post_news.php")
    Call<ReporterPostById> getReporterPostById(@Query("reporter_id") String reporter_id);

    @GET("gugrify_news/API/get_category_list.php")
    Call<NewsCategories> getCategoryList();

    @GET("gugrify_news/API/get_post_category_limit.php")
    Call<CategoryPosts> getPostByCategory(@Query("category") String category);

    @GET("gugrify_news/API/get_post_details.php")
    Call<PostDetailPojo> getPostDetails(@Query("post_id") String post_id);

    @GET("gugrify_news/API/get_post_category_limit.php")
    Call<TwentyPostsByCategory> getTwentyPostsByCategory(@Query("category") String category);

    @GET("gugrify_news/API/user_login")
    Call<LoginPojo> userLogin(@Query("user_id") String username, @Query("password") String password);

    @POST("gugrify_news/API/post_comments.php")
    Call<CommentsPostPojo> postComments(@Body CommentsPostPojo post);

}
