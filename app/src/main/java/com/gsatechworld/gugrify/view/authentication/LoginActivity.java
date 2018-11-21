package com.gsatechworld.gugrify.view.authentication;


/*
 * Creted By Ashish Pandey 25 Oct 18
 * */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.gsatechworld.gugrify.view.ActivityShowWebView;
import com.gsatechworld.gugrify.view.ReporterProfile;

import static com.gsatechworld.gugrify.utils.NetworkUtil.NO_INTERNET;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvSignUp, tv_signUp, tvTerms;
    Button btn_login;
    Button btn_googlePlus;
    com.facebook.login.widget.LoginButton btn_fb;
    CallbackManager callbackManager;
    Boolean fromDash;

    //for google sign in
    public static GoogleSignInClient mGoogleSignInClient;
    static final int RC_SIGN_IN = 1;
    GoogleSignInAccount account;

    EditText etLoginUserName, etLoginPassword;
    ApiInterface apiService;
    SharedPrefUtil sharedPrefUtil;
    NewsSharedPreferences sharedPreferences;
    String token, u, p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = NewsSharedPreferences.getInstance(this);
        fromDash = getIntent().getBooleanExtra("fromDash", false);

        InitViews();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);

        tvTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ActivityShowWebView.class);
                intent.putExtra("url", "www.gugrify.com/terms.php");
                startActivity(intent);
            }
        });


        btn_googlePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
                if (account != null) {
                    String token = account.getIdToken();
                    LoginActivity.mGoogleSignInClient.revokeAccess()
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // ...
                                }
                            });
                    return;
                }
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

       /* Button btn_signOut = findViewById(R.id.btn_signOut);
        btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut().addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "You have been signed out.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });*/

        //this code section is for FB login
        boolean loggedOut = AccessToken.getCurrentAccessToken() == null;

        if (!loggedOut) {
            //Picasso.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(200, 200)).into(imageView);
            Log.d("TAG", "Username is: " + Profile.getCurrentProfile().getName());

            //Using Graph API
            getUserProfile(AccessToken.getCurrentAccessToken());
        }

        btn_fb.setReadPermissions(Arrays.asList("email", "public_profile"));
        callbackManager = CallbackManager.Factory.create();

        btn_fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                sharedPreferences.setLoggedIn(true);
                getUserProfile(AccessToken.getCurrentAccessToken());
                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
                Log.d("API123", loggedIn + " ??");
                if (fromDash) {
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    sharedPreferences.setLoggedInUsingFB(true);
                    finish();
                } else
                    finish();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });//ends for FB login

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        if (account == null)
            Toast.makeText(LoginActivity.this, "Login failed!!", Toast.LENGTH_LONG).show();
        else {
            String token = account.getIdToken();
            sharedPreferences.setLoggedIn(true);
            sharedPreferences.setSharedPrefValue("user_id", account.getId());
            sharedPreferences.setSharedPrefValue("name", account.getDisplayName());
            sharedPreferences.setSharedPrefValue("email", account.getEmail());
            sharedPreferences.setSharedPrefValue("user_image", account.getPhotoUrl().toString());
            sharedPreferences.setLoggedInUsingGoogle(true);

            if (fromDash) {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            } else
                finish();
        }
    }


    private void getUserProfile(AccessToken currentAccessToken) {
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

                            sharedPreferences.setSharedPrefValue("name", first_name + " " + last_name);
                            sharedPreferences.setSharedPrefValue("email", email);
                            sharedPreferences.setSharedPrefValue("user_id", id);
                            sharedPreferences.setSharedPrefValue("user_image", image_url);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

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
        tvSignUp.setOnClickListener(this);
        btn_fb = findViewById(R.id.btn_fb);
        tv_signUp = findViewById(R.id.tv_signUp);
        tv_signUp.setOnClickListener(this);
        tvTerms = findViewById(R.id.tvTerms);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.btn_fb:

                break;

            case R.id.tv_signUp:
                intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.tvSignUp:
                intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_login:
                u = etLoginUserName.getText().toString();
                p = etLoginPassword.getText().toString();
                if (validate()) {
                    makeLoginPost();
                }
                break;

        }
    }

    public boolean validate() {
        if (u.trim().isEmpty()) {
            etLoginUserName.setError("Empty");
            etLoginUserName.setFocusable(true);
            return false;
        }
        if (p.trim().isEmpty()) {
            etLoginPassword.setError("Empty");
            etLoginPassword.setFocusable(true);
            return false;
        }
        return true;
    }

    public void makeLoginPost() {
        apiService = ApiClient.getClient().create(ApiInterface.class);

        if (NetworkUtil.getInstance(LoginActivity.this).isConnectingToInternet()) {
            Call<LoginPojo> call = apiService.userLogin(u, p);

            call.enqueue(new Callback<LoginPojo>() {
                @Override
                public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                    LoginPojo loginPojo = null;
                    if (response.isSuccessful()) {
                        Log.d("Reached here", "true");
                        loginPojo = response.body();
                        if (loginPojo.getResponse() != null && loginPojo.getResponse().equalsIgnoreCase("Failed")) {
                            Toast.makeText(LoginActivity.this, "Invalid credentials!!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Successfully Loged in!!", Toast.LENGTH_LONG).show();
                            sharedPreferences.setSharedPrefValue("user_id", loginPojo.getResult().getUser_id());
                            sharedPreferences.setSharedPrefValue("name", loginPojo.getResult().getName());
                            sharedPreferences.setSharedPrefValue("email", loginPojo.getResult().getEmail());
                            sharedPreferences.setSharedPrefValue("mobile_number", loginPojo.getResult().getMobileNumber());
                            sharedPreferences.setSharedPrefValue("user_image", loginPojo.getResult().getUser_image());
                            sharedPreferences.setSharedPrefValue("user_city", loginPojo.getResult().getUser_city());
                            sharedPreferences.setLoggedIn(true);
                            if (fromDash) {
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            } else
                                finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid credentials!!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginPojo> call, Throwable t) {
                    // Log error here since request failed
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "No internet !", Toast.LENGTH_LONG).show();
        }
    }

}