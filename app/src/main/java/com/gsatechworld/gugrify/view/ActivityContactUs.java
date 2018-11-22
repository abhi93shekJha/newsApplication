package com.gsatechworld.gugrify.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.SelectLanguageAndCities;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.ContactUsPojo;
import com.gsatechworld.gugrify.model.retrofit.LikePojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityContactUs extends AppCompatActivity{

    EditText mobile, email, name, message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);

        // clear FLAG_TRANSLUCENT_STATUS flag:
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_black));
        }

        email = findViewById(R.id.etContactEmail);
        name = findViewById(R.id.etContactName);
        message = findViewById(R.id.etContactMessage);
        mobile = findViewById(R.id.etContactMobile);

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    ContactUsPojo pojo = new ContactUsPojo(name.getText().toString(), mobile.getText().toString(), email.getText().toString(), message.getText().toString());
                    contactPost(pojo);
                }
            }
        });
    }

    public boolean validate(){
        if(mobile.getText().toString().trim().isEmpty()){
            mobile.setError("Empty");
            mobile.setFocusable(true);
            return false;
        }
        if(email.getText().toString().trim().isEmpty()){
            email.setError("Empty");
            email.setFocusable(true);
            return false;
        }
        if(name.getText().toString().trim().isEmpty()){
            name.setError("Empty");
            name.setFocusable(true);
            return false;
        }
        return true;
    }

    public void contactPost(ContactUsPojo pojo){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ContactUsPojo> call = apiService.contactUsRequest(pojo);

        call.enqueue(new Callback<ContactUsPojo>() {
            @Override
            public void onResponse(Call<ContactUsPojo> call, Response<ContactUsPojo> response) {
                ContactUsPojo contactUsPojo = null;
                if (response.isSuccessful()) {
                    Toast.makeText(ActivityContactUs.this, "Request sent!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ActivityContactUs.this, "Server error1!!", Toast.LENGTH_SHORT).show();
                }
//                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<ContactUsPojo>call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(ActivityContactUs.this, "Server error2!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
