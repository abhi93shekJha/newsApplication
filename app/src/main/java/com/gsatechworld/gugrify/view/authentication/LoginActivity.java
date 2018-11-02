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

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
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

import static com.gsatechworld.gugrify.utils.NetworkUtil.NO_INTERNET;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvSp2Login, tvSp4Register,tvSignUp;
    Button btn_login;
    Button btn_googlePlus;
  //  LoginButton btn_fb;
    GugrifyProgressDialog progressView = null;
    EditText etLoginUserName,etLoginPassword;
    ApiInterface apiInterface;
    SharedPrefUtil sharedPrefUtil;
    private RelativeLayout rl_login_main;
    String token;

    static final int RC_SIGN_IN = 1;
    GoogleSignInAccount account;
  //  CallbackManager callbackManager;

    /*
        GoogleSignInClient mGoogleSignInClient;
        static final int RC_SIGN_IN = 1;
        GoogleSignInAccount account;
    */
    String userName;
    String password;
    public static final String MY_PREFS_NAME = "MyPrefsFile";


    @BindView(R.id.ll_fb) LinearLayout ll_fb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

       /// boolean loggedOut = AccessToken.getCurrentAccessToken() == null;
       /// btn_fb = findViewById(R.id.btn_fb);

        /*if (!loggedOut) {
//            Picasso.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(200, 200)).into(imageView);
            Log.d("TAG", "Username is: " + Profile.getCurrentProfile().getName());

            //Using Graph API
            getUserProfile(AccessToken.getCurrentAccessToken());
        }

        btn_fb.setReadPermissions(Arrays.asList("email", "public_profile"));
        callbackManager = CallbackManager.Factory.create();

        btn_fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                //loginResult.getAccessToken();
                //loginResult.getRecentlyDeniedPermissions()
                //loginResult.getRecentlyGrantedPermissions()
                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
                Log.d("API123", loggedIn + " ??");

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);*/


        InitViews();

    }

  /*  private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }*/


    private void InitViews() {
        rl_login_main = findViewById(R.id.rl_login_main);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        etLoginUserName = findViewById(R.id.etLoginUserName);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(this);
        sharedPrefUtil = SharedPrefUtil.getInstance(LoginActivity.this);
        btn_googlePlus = findViewById(R.id.btn_googlePlus);
        btn_googlePlus.setOnClickListener(this);
        ll_fb.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_fb:
              //  btn_fb.performClick();
                break;
            case R.id.btn_googlePlus:
               /* account = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
                if (account != null){
                    token = account.getIdToken();
                    return;
                }
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);*/
                break;
            case R.id.btn_login:
                userName = etLoginUserName.getText().toString();
                String password = etLoginPassword.getText().toString();
                password = etLoginPassword.getText().toString();

              /*  if (!userName.equals("") && userName != null && !password.equals("") && password != null) {
                    userLogin(userName, password);
                }
                else {

                    CustomSnackBarUtil customSnackBarUtil = new CustomSnackBarUtil(LoginActivity.this);
                    customSnackBarUtil.showSnackBar(rl_login_main, getString(R.string.please_enter_the_credentials),
                            getResources().getColor(R.color.md_red_400),
                            getResources().getColor(R.color.colorWhite));
                }*/

                break;

            case R.id.tvSignUp:
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
                break;
        }
    }

  /*  private void userLogin(final String userName, String password) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if(NetworkUtil.getInstance(LoginActivity.this).isConnectingToInternet()){

            if (progressView == null) {
                progressView = new GugrifyProgressDialog(LoginActivity.this);
            }
            progressView.showProgressView("Sending data to server", "Please wait...");

            //LoginResponse user = new LoginResponse(userName, password);
            Call<LoginResponse> call = apiInterface.getLoginDetails(userName,password);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse = response.body();

                    Log.e("LOGIN", "LOGIN Response--> " + loginResponse);
                    progressView.closeProgressView();

                    if (loginResponse.getResponse() != null &&
                            loginResponse.getResponse().equalsIgnoreCase("Success")){

                        String id = loginResponse.getResult().getId();
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putString("userId", id);
                        editor.apply();

                        Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
                        intent1.putExtra("value", "0");
                        startActivity(intent1);
                        finish();
                        sharedPrefUtil.setPrefrenceBoolean("LoginStatus",true);


                    } else {
                        CustomSnackBarUtil customSnackBarUtil = new CustomSnackBarUtil(LoginActivity.this);
                        customSnackBarUtil.showSnackBar(rl_login_main, "Invalid Register Details \n Please try again",
                                getResources().getColor(R.color.md_red_400),
                                getResources().getColor(R.color.colorWhite));
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressView.closeProgressView();
                    CustomSnackBarUtil customSnackBarUtil = new CustomSnackBarUtil(LoginActivity.this);
                    customSnackBarUtil.showSnackBar(rl_login_main, "On Failure",
                            getResources().getColor(R.color.md_red_400),
                            getResources().getColor(R.color.colorWhite));


                }
            });

        } else {
            CustomSnackBarUtil customSnackBarUtil = new CustomSnackBarUtil(this);
            customSnackBarUtil.showSnackBar(rl_login_main, NO_INTERNET,
                    getResources().getColor(R.color.md_red_400),
                    getResources().getColor(R.color.colorWhite));
        }
    }*/


 /*   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Message", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    public void updateUI(GoogleSignInAccount account) {
        if (account == null){

        }
        else{
            //make a post request here.
            token = account.getIdToken();
            Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent1);
        }
    }*/

}