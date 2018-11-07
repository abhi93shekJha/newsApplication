package com.gsatechworld.gugrify.view;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.SelectLanguageAndCities;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.GetMainAdvertisement;
import com.gsatechworld.gugrify.model.retrofit.NewsCategories;
import com.gsatechworld.gugrify.model.retrofit.ReporterPost;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReporterPostActivity extends AppCompatActivity implements View.OnClickListener {
    List<String> languages, categories;
    LinearLayout twelveImages;
    LinearLayout twelveTexts;
    Typeface fontRegular;
    static String categroySelected;
    final private int REQUEST_CODE_ASK_PERMISSIONS_CAMERA = 100;
    final private int REQUEST_CODE_ASK_PERMISSIONS_EXTERNAL_STORAGE = 200;
    protected static final int GALLERY_PICTURE = 0, CAMERA_REQUEST = 1;
    static ImageView forEverywhereImage;
    int selectedImage;
    boolean[] imagesPresent;
    Toolbar toolbar;
    ImageView image0, image3, image4, image5, image6, image7, image8, image9, image10, image11, image12;
    EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9, editText10, editText11, editText12;
    EditText newsHeadline, newsBrief, newsDescription, et_youtubeId;
    NewsSharedPreferences sharedPreferences;

    //variables for post request
    String[] imageArray, textsArray;
    String mainImage;
    String text1, text2, text3, text4, text5, text6, text7, text8, text9, text10, text11, text12, headline, description, brief, youtube_id;

    private Intent pictureActionIntent = null;
    Bitmap bitmap;
    String selectedImagePath, encodedBase64;
    ApiInterface apiService;
    ScrollView main_layout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter_post);

        main_layout = findViewById(R.id.main_layout);
        progressBar = findViewById(R.id.progressBar);
        et_youtubeId = findViewById(R.id.et_youtubeId);

        sharedPreferences = NewsSharedPreferences.getInstance(ReporterPostActivity.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Gugrify");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);

        //getting the categories if not already present
        if (DashboardActivity.newsCategories.getCategory() == null) {
            getReporterCategories();
        }

        textsArray = new String[12];
        imageArray = new String[10];
        mainImage = "";
        imagesPresent = new boolean[11];

        languages = new ArrayList<>();
        categories = new ArrayList<>();
        twelveImages = findViewById(R.id.imageListTableLayout);
        twelveTexts = findViewById(R.id.editTextListLinearLayout);
        fontRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

        newsHeadline = findViewById(R.id.newsHeadline);
        newsDescription = findViewById(R.id.newsDescription);
        newsBrief = findViewById(R.id.newsBrief);
        et_youtubeId = findViewById(R.id.et_youtubeId);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        editText6 = findViewById(R.id.editText6);
        editText7 = findViewById(R.id.editText7);
        editText8 = findViewById(R.id.editText8);
        editText9 = findViewById(R.id.editText9);
        editText10 = findViewById(R.id.editText10);
        editText11 = findViewById(R.id.editText11);
        editText12 = findViewById(R.id.editText12);

        Button btn_news_submit = findViewById(R.id.btn_news_submit);

        image0 = findViewById(R.id.news_main_image);
        image0.setOnClickListener(this);
        image0.setTag(0);
        image3 = findViewById(R.id.image3);
        image3.setOnClickListener(this);
        image3.setTag(1);
        image4 = findViewById(R.id.image4);
        image4.setOnClickListener(this);
        image4.setTag(2);
        image5 = findViewById(R.id.image5);
        image5.setOnClickListener(this);
        image5.setTag(3);
        image6 = findViewById(R.id.image6);
        image6.setOnClickListener(this);
        image6.setTag(4);
        image7 = findViewById(R.id.image7);
        image7.setOnClickListener(this);
        image7.setTag(5);
        image8 = findViewById(R.id.image8);
        image8.setOnClickListener(this);
        image8.setTag(6);
        image9 = findViewById(R.id.image9);
        image9.setOnClickListener(this);
        image9.setTag(7);
        image10 = findViewById(R.id.image10);
        image10.setOnClickListener(this);
        image10.setTag(8);
        image11 = findViewById(R.id.image11);
        image11.setOnClickListener(this);
        image11.setTag(9);
        image12 = findViewById(R.id.image12);
        image12.setOnClickListener(this);
        image12.setTag(10);

        //Spinner for input type
        final Spinner spinner = findViewById(R.id.newsTypeSelectionSpinner);
        languages.add("Texts");
        languages.add("Images");
        languages.add("Select");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages) {

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }
                return v;
            }

            @Override
            public int getCount() {
                return (languages.size() - 1); // you dont display last item. It is used as hint.
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(dataAdapter.getCount());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (languages.get(i).equals("Select")) {
                    twelveImages.setVisibility(View.GONE);
                    twelveTexts.setVisibility(View.GONE);
                } else if (languages.get(i).equals("Texts")) {
                    twelveImages.setVisibility(View.GONE);
                    twelveTexts.setVisibility(View.VISIBLE);
                } else {
                    twelveImages.setVisibility(View.VISIBLE);
                    twelveTexts.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //button for submitting news
        btn_news_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateCommon())
                    return;
                if (spinner.getSelectedItem().toString().equalsIgnoreCase("Texts")) {
                    if (!validateTexts()) {
                        return;
                    }
                    if (!imagesPresent[0]) {
                        Toast.makeText(ReporterPostActivity.this, "Please insert all the images.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ReporterPost post;
                    post = new ReporterPost(sharedPreferences.getSharedPrefValue("reporterId"), mainImage, "text_arrays", "",headline, brief, description, youtube_id, categroySelected, sharedPreferences.getSharedPrefValue("reporterLanguage"), textsArray, imageArray, sharedPreferences.getSharedPrefValue("reporterCity"));
                    makeANewsPost(post);
                } else {
                    for (int i = 0; i < imagesPresent.length; i++) {
                        if (!imagesPresent[i]) {
                            Toast.makeText(ReporterPostActivity.this, "Please insert all the images.", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    ReporterPost post;
                    post = new ReporterPost(sharedPreferences.getSharedPrefValue("reporterId"), mainImage, "text_arrays", "",headline, brief, description, youtube_id, categroySelected, sharedPreferences.getSharedPrefValue("reporterLanguage"), textsArray, imageArray, sharedPreferences.getSharedPrefValue("reporterCity"));
                    makeANewsPost(post);
                }
            }
        });//end of submitting news

        //Spinner for category (has to be fetched from API)
        Spinner categoriesSpinner = findViewById(R.id.categorySelectionSpinner);
       /* categories.add("Sports");
        categories.add("Business");
        categories.add("Arts and Culture");
        categories.add("Entertainment");
        categories.add("Education");
        categories.add("Select");*/

       for(int i=0; i<DashboardActivity.newsCategories.getCategory().size(); i++){
           categories.add(DashboardActivity.newsCategories.getCategory().get(i));
       }
        categories.add("Select news category");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories) {

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }
                return v;
            }

            @Override
            public int getCount() {
                return (categories.size() - 1); // you dont display last item. It is used as hint.
            }
        };
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(categoryAdapter);
        categoriesSpinner.setSelection(categoryAdapter.getCount());
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (categories.get(i).equals("Select")) {

                } else {
                    categroySelected = categories.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        forEverywhereImage = (ImageView) view;
        selectedImage = (int) view.getTag();
        startDialog();
    }

    void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(ReporterPostActivity.this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent pictureActionIntent = null;
                        pictureActionIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pictureActionIntent, GALLERY_PICTURE);
                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

//                        requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS_CAMERA);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        /*File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));*/
                        // Open your camera here.
                        startActivityForResult(intent, CAMERA_REQUEST);
                    }
                });
        myAlertDialog.show();
    }

    //Result from opening camera or gallery image selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Bitmap convertedImage = getResizedBitmap(thumbnail, 600);

                //setting into image Array to make a post
                if (selectedImage == 0) {
                    mainImage = getStringImage(convertedImage);
                    imagesPresent[selectedImage] = true;
                } else {
                    imageArray[selectedImage - 1] = getStringImage(convertedImage);
                    imagesPresent[selectedImage] = true;
                }

                forEverywhereImage.setImageBitmap(convertedImage);
                if (checkIfExternalStoragePresent())
                    saveImage(thumbnail);
                else {
                    String savedPath = saveToInternalStorage(thumbnail);
                    Log.d("Image saved to", savedPath);
                }
                Toast.makeText(ReporterPostActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            if (data != null) {

                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    Bitmap convertedImage = getResizedBitmap(bitmap, 600);

                    //setting into image Array to make a post
                    imageArray[selectedImage] = getStringImage(convertedImage);
                    imagesPresent[selectedImage] = true;

//                    String path = saveImage(bitmap);
                    Toast.makeText(ReporterPostActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    forEverywhereImage.setImageBitmap(convertedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ReporterPostActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    //converting image to Base64Encode
    private String getBase64Encode(String path) {
        try {
//            Log.d(SignUpActivity.class.getSimpleName(), "" + sharedPref.getProfilePath());

            InputStream inputStream = new FileInputStream(path);//You can get an inputStream using any IO API
            byte[] bytes;
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            bytes = output.toByteArray();
            encodedBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return encodedBase64;
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "/gugrify");
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
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

    public boolean checkIfExternalStoragePresent() {
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();

        if (isSDSupportedDevice && isSDPresent) {
            return true;
        } else {
            return false;
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, System.currentTimeMillis() + "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public boolean validateCommon() {
        youtube_id = et_youtubeId.getText().toString();
        headline = newsHeadline.getText().toString();
        brief = newsBrief.getText().toString();
        description = newsDescription.getText().toString();
        if (headline.trim().isEmpty()) {
            newsHeadline.setError("Empty");
            newsHeadline.setFocusable(true);
            return false;
        }
        if (brief.trim().isEmpty()) {
            newsBrief.setError("Empty");
            newsBrief.setFocusable(true);
            return false;
        }
        if (description.trim().isEmpty()) {
            newsDescription.setError("Empty");
            newsDescription.setFocusable(true);
            return false;
        }
        return true;
    }

    public boolean validateTexts() {

        text1 = editText1.getText().toString();
        textsArray[0] = text1;
        text2 = editText2.getText().toString();
        textsArray[1] = text2;
        text3 = editText3.getText().toString();
        textsArray[2] = text3;
        text4 = editText4.getText().toString();
        textsArray[3] = text4;
        text5 = editText5.getText().toString();
        textsArray[4] = text5;
        text6 = editText6.getText().toString();
        textsArray[5] = text6;
        text7 = editText7.getText().toString();
        textsArray[6] = text7;
        text8 = editText8.getText().toString();
        textsArray[7] = text8;
        text9 = editText9.getText().toString();
        textsArray[8] = text9;
        text10 = editText10.getText().toString();
        textsArray[9] = text10;
        text11 = editText11.getText().toString();
        textsArray[10] = text11;
        text12 = editText12.getText().toString();
        textsArray[11] = text12;

        if (text1.trim().isEmpty()) {
            editText1.setError("Empty");
            editText1.setFocusable(true);
            return false;
        }
        if (text2.trim().isEmpty()) {
            editText2.setError("Empty");
            editText2.setFocusable(true);
            return false;
        }
        if (text3.trim().isEmpty()) {
            editText3.setError("Empty");
            editText3.setFocusable(true);
            return false;
        }
        if (text4.trim().isEmpty()) {
            editText4.setError("Empty");
            editText4.setFocusable(true);
            return false;
        }
        if (text5.trim().isEmpty()) {
            editText5.setError("Empty");
            editText5.setFocusable(true);
            return false;
        }
        if (text6.trim().isEmpty()) {
            editText6.setError("Empty");
            editText6.setFocusable(true);
            return false;
        }
        if (text7.trim().isEmpty()) {
            editText7.setError("Empty");
            editText7.setFocusable(true);
            return false;
        }
        if (text8.trim().isEmpty()) {
            editText8.setError("Empty");
            editText8.setFocusable(true);
            return false;
        }
        if (text9.trim().isEmpty()) {
            editText9.setError("Empty");
            editText9.setFocusable(true);
            return false;
        }
        if (text10.trim().isEmpty()) {
            editText10.setError("Empty");
            editText10.setFocusable(true);
            return false;
        }
        if (text11.trim().isEmpty()) {
            editText11.setError("Empty");
            editText11.setFocusable(true);
            return false;
        }
        if (text12.trim().isEmpty()) {
            editText12.setError("Empty");
            editText12.setFocusable(true);
            return false;
        }
        return true;
    }

    //Bitmap to Base64
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void makeANewsPost(ReporterPost post) {

        main_layout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ReporterPost> call = apiService.postReporterNews(post);

        call.enqueue(new Callback<ReporterPost>() {
            @Override
            public void onResponse(Call<ReporterPost> call, Response<ReporterPost> response) {
                ReporterPost responseOfPosting = null;
                if (response.isSuccessful()) {
                    responseOfPosting = response.body();

                    Toast.makeText(ReporterPostActivity.this, "News successfully posted!!!", Toast.LENGTH_LONG).show();

                    main_layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(ReporterPostActivity.this, "Server error!!", Toast.LENGTH_SHORT);

                    main_layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ReporterPost> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(ReporterPostActivity.this, "Server error!!", Toast.LENGTH_SHORT);

                main_layout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void getReporterCategories() {

        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<NewsCategories> call = apiService.getCategoryList();

        call.enqueue(new Callback<NewsCategories>() {
            @Override
            public void onResponse(Call<NewsCategories> call, Response<NewsCategories> response) {

                if (response.isSuccessful()) {
                    Log.d("Reached here", "true");
                    DashboardActivity.newsCategories = response.body();

                } else {
                    Toast.makeText(ReporterPostActivity.this, "Server error!!", Toast.LENGTH_SHORT);

                }
            }

            @Override
            public void onFailure(Call<NewsCategories> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(ReporterPostActivity.this, "Server error!!", Toast.LENGTH_SHORT);
            }
        });
    }
}
