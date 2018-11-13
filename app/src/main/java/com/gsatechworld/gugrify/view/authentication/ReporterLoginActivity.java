package com.gsatechworld.gugrify.view.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.GetMainAdvertisement;
import com.gsatechworld.gugrify.model.retrofit.ReporterLogin;
import com.gsatechworld.gugrify.view.ReporterProfile;
import com.gsatechworld.gugrify.view.SplashActivity;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReporterLoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btn_login;
    String u, p;
    ApiInterface apiService;
    NewsSharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter_login);
        sharedPreferences = NewsSharedPreferences.getInstance(this);
        username = findViewById(R.id.etLoginUserName);
        password = findViewById(R.id.etLoginPassword);
        if(sharedPreferences.getLoggedInUsingGoogle() || sharedPreferences.getLoggedInUsingFB() || sharedPreferences.getIsLoggedIn()){
            Toast.makeText(this, "First logout as a user to login as a reporter!!", Toast.LENGTH_LONG).show();
            finish();
        }
        if(sharedPreferences.getSharedPrefValueBoolean("reporterLoggedIn")){
            Intent intent = new Intent(ReporterLoginActivity.this, ReporterProfile.class);
            startActivity(intent);
            finish();
        }
        btn_login = findViewById(R.id.btn_login);
        sharedPreferences = NewsSharedPreferences.getInstance(ReporterLoginActivity.this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u = username.getText().toString();
                p = password.getText().toString();
                if(validate()){
                    makeLoginPost();
                }
            }
        });
    }

    public boolean validate(){
        if(u.trim().isEmpty()){
            username.setError("Empty");
            username.setFocusable(true);
            return false;
        }
        if(p.trim().isEmpty()){
            password.setError("Empty");
            password.setFocusable(true);
            return false;
        }
        return true;
    }

    public void makeLoginPost(){

        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ReporterLogin> call = apiService.reporterLogin(u,p, "reporter");

        call.enqueue(new Callback<ReporterLogin>() {
            @Override
            public void onResponse(Call<ReporterLogin> call, Response<ReporterLogin> response) {
                ReporterLogin reporterDetail = null;
                if (response.isSuccessful()) {
                    Log.d("Reached here", "true");
                    reporterDetail = response.body();
                    if(reporterDetail.getResponse() != null && reporterDetail.getResponse().equalsIgnoreCase("Failed")){
                        Toast.makeText(ReporterLoginActivity.this, "Invalid credentials!!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Log.d("id is", reporterDetail.getReporter_id() + reporterDetail.getLanguage());
                        sharedPreferences.setSharedPrefValue("reporterId", reporterDetail.getReporter_id());
                        sharedPreferences.setSharedPrefValue("reporterLanguage", reporterDetail.getLanguage());
                        sharedPreferences.setSharedPrefValue("reporterCity", reporterDetail.getReporter_place());
                        sharedPreferences.setSharedPrefValue("reporterName", reporterDetail.getReporter_name());
                        sharedPreferences.setSharedPrefValue("reporterPic", reporterDetail.getReporter_pic());
                        sharedPreferences.setSharedPrefValue("reporterAdsCount", reporterDetail.getTotal_ads_count());
                        sharedPreferences.setSharedPrefValue("reporterPostsCount", reporterDetail.getTotal_posts_count());
                        Intent intent = new Intent(ReporterLoginActivity.this, ReporterProfile.class);
                        sharedPreferences.setSharedPrefValueBoolean("reporterLoggedIn", true);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(ReporterLoginActivity.this, "Invalid credentials!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReporterLogin> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(ReporterLoginActivity.this, "Server error!! Try again.", Toast.LENGTH_LONG).show();
            }
        });

    }
}
