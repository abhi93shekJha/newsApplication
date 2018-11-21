package com.gsatechworld.gugrify.view.authentication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.SelectLanguageAndCities;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.City;
import com.gsatechworld.gugrify.model.retrofit.CityResponse;
import com.gsatechworld.gugrify.model.retrofit.UserRegistrationPojo;
import com.gsatechworld.gugrify.utils.CustomSnackBarUtil;
import com.gsatechworld.gugrify.utils.NetworkUtil;
import com.gsatechworld.gugrify.utils.SharedPrefUtil;
import com.gsatechworld.gugrify.utils.Utility;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gsatechworld.gugrify.utils.NetworkUtil.NO_INTERNET;


public class RegistrationActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    SharedPrefUtil sharedPrefUtil;
    EditText etUsername, etUserId, etEmail, etMobileNumber, etUserCity, etPassword, etConfirmPassword;
    String Username = "", user_id = "", email = "", mobile = "", city = "", password = "", confirmPassword = "";
    Button btn_register;
    private RelativeLayout rl_register_main;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private ImageView ivImage;
    private String userChoosenTask, ImageInBase64;
    private LinearLayout ll_takePic;
    Bitmap bm, thumbnail;
    FileOutputStream fo = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Username = etUsername.getText().toString();
                user_id = etUserId.getText().toString();
                email = etEmail.getText().toString();
                mobile = etMobileNumber.getText().toString();
                city = etUserCity.getText().toString();
                password = etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();

                //userRegister(name,mobile,user_id,pwd,inviteCode);

                if (!Username.toString().trim().equalsIgnoreCase("")
                        && !user_id.toString().trim().equalsIgnoreCase("")
                        && !email.toString().trim().equalsIgnoreCase("")
                        && !mobile.toString().trim().equalsIgnoreCase("")
                        && !city.toString().trim().equalsIgnoreCase("")
                        && !password.toString().trim().equalsIgnoreCase("")
                        && !confirmPassword.toString().trim().equalsIgnoreCase("")) {

                    if (mobile.toString().trim().length() < 10) {
                        Toast.makeText(RegistrationActivity.this, "Please enter valid mobile number!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (thumbnail != null) {
                        if (!password.equals(confirmPassword)) {
                            Toast.makeText(getApplicationContext(), "Enter matching passwords!!", Toast.LENGTH_SHORT).show();
                        } else {
                            thumbnail = getResizedBitmap(thumbnail, 600);
                            ImageInBase64 = getStringImage(thumbnail);
                            Log.d("From camera is", ImageInBase64);
                            UserRegistrationPojo pojo = new UserRegistrationPojo(Username, user_id, email, mobile, city, ImageInBase64, password);
                            userRegister(pojo);
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        }
                    } else if (bm != null) {
                        if (!password.equals(confirmPassword)) {
                            Toast.makeText(getApplicationContext(), "Enter matching passwords!!", Toast.LENGTH_SHORT).show();
                        } else {
                            bm = getResizedBitmap(bm, 600);
                            ImageInBase64 = getStringImage(bm);
                            Log.d("From gallery", ImageInBase64);
                            UserRegistrationPojo pojo = new UserRegistrationPojo(Username, user_id, email, mobile, city, ImageInBase64, password);
                            userRegister(pojo);
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void userRegister(UserRegistrationPojo pojo) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        if (NetworkUtil.getInstance(RegistrationActivity.this).isConnectingToInternet()) {
            Call<UserRegistrationPojo> call = apiService.createUserRegistration(pojo);
            call.enqueue(new Callback<UserRegistrationPojo>() {
                @Override
                public void onResponse(Call<UserRegistrationPojo> call, Response<UserRegistrationPojo> response) {
                    UserRegistrationPojo regResponse = null;
                    if (response.isSuccessful()) {
                        regResponse = response.body();
                        if (regResponse.getResult() instanceof String) {
                            if (regResponse.getResult().toString().trim().equalsIgnoreCase("success")) {
                                Toast.makeText(RegistrationActivity.this, "Successfully registered!!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else
                                Toast.makeText(RegistrationActivity.this, regResponse.getResult().toString(), Toast.LENGTH_SHORT).show();
                        }

                        if (regResponse.getResult() instanceof ArrayList<?>) {
                            ArrayList<String> result = (ArrayList<String>) regResponse.getResult();
                            String error = "Registration failed!!";
                            for (int i = 0; i < result.size(); i++) {
                                error = error +"\n"+ result.get(i);
                            }
                            Toast.makeText(RegistrationActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
//                Log.d(TAG, "Number of movies received: " + movies.size());
                }

                @Override
                public void onFailure(Call<UserRegistrationPojo> call, Throwable t) {
                    // Log error here since request failed
                    Toast.makeText(RegistrationActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(RegistrationActivity.this, "No internet!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(RegistrationActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        //    FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(RegistrationActivity.this.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivImage.setImageBitmap(bm);
    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        Log.d("Original pixels are", String.valueOf(width) + " " + String.valueOf(height));

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        Log.d("New pixels are", String.valueOf(width) + " " + String.valueOf(height));
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    /*For Usinf Image Convert*/
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void initViews() {
        rl_register_main = findViewById(R.id.rl_register_main);
        sharedPrefUtil = SharedPrefUtil.getInstance(RegistrationActivity.this);

        btn_register = findViewById(R.id.btn_register);
        etUsername = findViewById(R.id.etUsername);
        etUserId = findViewById(R.id.etUserId);
        etEmail = findViewById(R.id.etEmail);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etUserCity = findViewById(R.id.etUserCity);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        ll_takePic = (LinearLayout) findViewById(R.id.ll_takePic);
        ivImage = (ImageView) findViewById(R.id.ivImage);

        ll_takePic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

    }

    /*private void userRegister(String name, String mobileNumber,String emailId,String pwd,String inviteCode) {

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
