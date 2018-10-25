package com.gsatechworld.gugrify.view.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.utils.CustomSnackBarUtil;
import com.gsatechworld.gugrify.utils.NetworkUtil;
import com.gsatechworld.gugrify.utils.SharedPrefUtil;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gsatechworld.gugrify.utils.NetworkUtil.NO_INTERNET;


public class RegistrationActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    SharedPrefUtil sharedPrefUtil;
    EditText etLoginName,etLoginMobileNumber,etLoginUserid,etLoginPassword,etRefferCode;
    Button btn_register;
   // private RelativeLayout rl_register_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* String name="",mobile="",user_id="",pwd="",inviteCode="";
                name = etLoginName.getText().toString();
                mobile = etLoginMobileNumber.getText().toString();
                user_id = etLoginUserid.getText().toString();
                pwd= etLoginPassword.getText().toString();
                inviteCode = etRefferCode.getText().toString();
                userRegister(name,mobile,user_id,pwd,inviteCode);*/

                startActivity(new Intent(RegistrationActivity.this,DashboardActivity.class));
            }
        });
    }

    private void initViews(){
       // rl_register_main = findViewById(R.id.rl_register_main);
        sharedPrefUtil = SharedPrefUtil.getInstance(RegistrationActivity.this);

        btn_register = findViewById(R.id.btn_register);
        etLoginName = findViewById(R.id.etLoginName);
        etLoginMobileNumber = findViewById(R.id.tvLoginMobileNumber);
        etLoginUserid = findViewById(R.id.tvLoginUserid);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        etRefferCode = findViewById(R.id.tvRefferCode);



    }

   /* private void userRegister(String name, String mobileNumber,String emailId,String pwd,String inviteCode) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if(NetworkUtil.getInstance(RegistrationActivity.this).isConnectingToInternet()){



            RegisterResponse user = new RegisterResponse(name,mobileNumber,emailId,pwd,inviteCode);
            Call<RegisterResponse> call = apiInterface.userRegister(user);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    RegisterResponse registerResponse = response.body();

                    Log.e("Register", "Register Response--> " + registerResponse);
                    if (registerResponse.response != null &&
                            registerResponse.response.equalsIgnoreCase("success")) {


                        Intent intent1 = new Intent(RegistrationActivity.this, LoginActivity.class);
                        intent1.putExtra("value", "0");
                        startActivity(intent1);
                        Toast.makeText(RegistrationActivity.this,registerResponse.result.getOtp(),Toast.LENGTH_SHORT).show();
                        sharedPrefUtil.setPrefrence("name",registerResponse.result.getName());
                        sharedPrefUtil.setPrefrence("mobile",registerResponse.result.getPhone());
                        sharedPrefUtil.setPrefrence("email",registerResponse.result.getEmail());
                        sharedPrefUtil.setPrefrence("pwd",registerResponse.result.getPassword());
                        sharedPrefUtil.setPrefrence("otp",registerResponse.result.getOtp());
                        sharedPrefUtil.setPrefrence("referralCode",registerResponse.result.getReferral_code());




                    } else {
                        CustomSnackBarUtil customSnackBarUtil = new CustomSnackBarUtil(RegistrationActivity.this);
                        customSnackBarUtil.showSnackBar(rl_register_main, NO_INTERNET,
                                getResources().getColor(R.color.md_red_400),
                                getResources().getColor(R.color.colorWhite));
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    CustomSnackBarUtil customSnackBarUtil = new CustomSnackBarUtil(RegistrationActivity.this);
                    customSnackBarUtil.showSnackBar(rl_register_main, "On Failure",
                            getResources().getColor(R.color.md_red_400),
                            getResources().getColor(R.color.colorWhite));
                }
            });

        } else {
            CustomSnackBarUtil customSnackBarUtil = new CustomSnackBarUtil(this);
            customSnackBarUtil.showSnackBar(rl_register_main, NO_INTERNET,
                    getResources().getColor(R.color.md_red_400),
                    getResources().getColor(R.color.colorWhite));
        }
    }*/
}
