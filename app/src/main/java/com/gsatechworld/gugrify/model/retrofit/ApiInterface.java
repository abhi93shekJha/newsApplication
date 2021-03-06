package com.gsatechworld.gugrify.model.retrofit;

import com.gsatechworld.gugrify.model.CommentsPostPojo;
import com.gsatechworld.gugrify.model.PostsByCategory;
import com.gsatechworld.gugrify.model.ScrollNewsPojo;
import com.gsatechworld.gugrify.model.TopNewsPojo;
import com.gsatechworld.gugrify.view.authentication.LoginPojo;
import com.gsatechworld.gugrify.view.playlist.CreatePlayListPojo;
import com.gsatechworld.gugrify.view.playlist.GetPlaylistsPojo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("API/lang_city_list.php")
    Call<CityResponse> getAllCities(@Query("lang_id") String id);

    @GET("API/language_list.php")
    Call<LanguageResponse> getAllLanguages();

    @GET("API/latest_news.php")
    Call<LanguageResponse> getLatestNews(@Query("city") String city);

    @POST("API/post_news.php")
    Call<ReporterPost> postReporterNews(@Body ReporterPost post);

    @GET("API/get_audio.php")
    Call<GetMainAdvertisement> getMainAdvertisement();

    @GET("API/get_ads.php")
    Call<CityWiseAdvertisement> getReporterAdvertisement(@Query("city") String city);

    @GET("API/reporter_login")
    Call<ReporterLogin> reporterLogin(@Query("username") String username, @Query("password") String password, @Query("role") String role);

    @GET("API/post_news.php")
    Call<ReporterPostById> getReporterPostById(@Query("reporter_id") String reporter_id);

    @GET("API/get_category_list.php")
    Call<NewsCategories> getCategoryList();

    @GET("API/get_post_category_limit.php")
    Call<CategoryPosts> getPostByCategory(@Query("category") String category);

    @GET("API/get_post_details.php")
    Call<PostDetailPojo> getPostDetails(@Query("post_id") String post_id);

    @GET("API/get_post_category_limit.php")
    Call<TwentyPostsByCategory> getTwentyPostsByCategory(@Query("category") String category);

    @GET("API/user_login")
    Call<LoginPojo> userLogin(@Query("user_id") String username, @Query("password") String password);

    @POST("API/post_comments.php")
    Call<CommentsPostPojo> postComments(@Body CommentsPostPojo post);

    @GET("API/latest_news.php")
    Call<LatestNewsByCity> getLatestNewsByCity(@Query("city") String city);

    @GET("API/create_playlist.php")
    Call<GetPlaylistsPojo> getPlaylists(@Query("user_id") String user_id);

    @POST("API/create_playlist.php")
    Call<CreatePlayListPojo> createPlaylist(@Body CreatePlayListPojo post);

    @GET("API/get_active_posts.php")
    Call<ActivePostsPojo> getActivePosts(@Query("first") String first, @Query("last") String last);

    @POST("API/user_signup.php")
    Call<UserRegistrationPojo> createUserRegistration(@Body UserRegistrationPojo post);

    @GET("API/get_playlist_data.php")
    Call<GetPostsByPlaylistId> getPostsByPlaylistId(@Query("user_id") String user_id, @Query("playlist_id") String playlist_id);

    @GET("API/get_scrolling_scroll_news.php")
    Call<ScrollNewsPojo> getscrollingNews();

    @GET("API/get_scrolling_top_news.php")
    Call<TopNewsPojo> getTopNews();

    @GET("API/get_search.php")
    Call<HeadlineSearchPojo> getSearchedHeadlines(@Query("search") String search);

    @POST("API/get_post_details.php")
    Call<ViewPojo> makeAView(@Query("post_id") String post_id);

    @POST("API/post_likes.php")
    Call<LikePojo> likeAPost(@Body LikePojo pojo);

    @POST("API/post_contactus.php")
    Call<ContactUsPojo> contactUsRequest(@Body ContactUsPojo post);

    @POST("API/get_playlist_data.php")
    Call<PlaylistPostPojo> addPostToPlaylist(@Body PlaylistPostPojo post);

    @GET("API/get_youtube.php")
    Call<YoutubeResponsePojo> makeYoutubeVideoRequest();

    @GET("API/get_reporter_ads.php")
    Call<GetReporterAdsPojo> getReporterAds(@Query("reporter_id") String post_id);
}
