package com.gsatechworld.gugrify.view.authentication;


/*
* Creted By Ashish Pandey 25 Oct 18
* */

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.ReporterLogin;
import com.gsatechworld.gugrify.utils.CustomSnackBarUtil;
import com.gsatechworld.gugrify.utils.GugrifyProgressDialog;
import com.gsatechworld.gugrify.utils.NetworkUtil;
import com.gsatechworld.gugrify.utils.SharedPrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.gsatechworld.gugrify.view.ReporterProfile;

import static com.gsatechworld.gugrify.utils.NetworkUtil.NO_INTERNET;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvSignUp;
    Button btn_login;
    Button btn_googlePlus;

    EditText etLoginUserName,etLoginPassword;
    ApiInterface apiService;
    SharedPrefUtil sharedPrefUtil;
    NewsSharedPreferences sharedPreferences;
    String token, u, p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = NewsSharedPreferences.getInstance(this);

        InitViews();

    }


    private void InitViews() {
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        etLoginUserName = findViewById(R.id.etLoginUserName);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(this);
        sharedPrefUtil = SharedPrefUtil.getInstance(LoginActivity.this);
        btn_googlePlus = findViewById(R.id.btn_googlePlus);
        btn_googlePlus.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.ll_fb:
              //  btn_fb.performClick();
                break;
            case R.id.btn_googlePlus:

                break;

            case R.id.tvSignUp:
                intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_login:
                u = etLoginUserName.getText().toString();
                p = etLoginPassword.getText().toString();
                if(validate()){
                    makeLoginPost();
                }
                break;
        }
    }

    public boolean validate(){
        if(u.trim().isEmpty()){
            etLoginUserName.setError("Empty");
            etLoginUserName.setFocusable(true);
            return false;
        }
        if(p.trim().isEmpty()){
            etLoginPassword.setError("Empty");
            etLoginPassword.setFocusable(true);
            return false;
        }
        return true;
    }

    public void makeLoginPost(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginPojo> call = apiService.userLogin(u,p);

        call.enqueue(new Callback<LoginPojo>() {
            @Override
            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                LoginPojo loginPojo = null;
                if (response.isSuccessful()) {
                    Log.d("Reached here", "true");
                    loginPojo = response.body();
                    if(loginPojo.getResult().getResponse() != null && loginPojo.getResult().getResponse().equalsIgnoreCase("Failed")){
                        Toast.makeText(LoginActivity.this, "Invalid credentials!!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        sharedPreferences.setSharedPrefValue("user_id", loginPojo.getResult().getUser_id());
                        sharedPreferences.setSharedPrefValue("name", loginPojo.getResult().getName());
                        sharedPreferences.setSharedPrefValue("email", loginPojo.getResult().getEmail());
                        sharedPreferences.setSharedPrefValue("mobile_number", loginPojo.getResult().getMobileNumber());
                        sharedPreferences.setSharedPrefValue("user_image", loginPojo.getResult().getUser_image());
                        sharedPreferences.setSharedPrefValue("user_city", loginPojo.getResult().getUser_city());
                        sharedPreferences.setLoggedIn(true);
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginPojo> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(LoginActivity.this, "Server error!! Try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

}