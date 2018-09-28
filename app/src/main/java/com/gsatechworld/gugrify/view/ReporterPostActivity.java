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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.SelectLanguageAndCities;

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

public class ReporterPostActivity extends AppCompatActivity implements View.OnClickListener{
    List<String> languages, categories;
    TableLayout twelveImages;
    LinearLayout twelveTexts;
    Typeface fontRegular;
    static String categroySelected;
    final private int REQUEST_CODE_ASK_PERMISSIONS_CAMERA = 100;
    final private int REQUEST_CODE_ASK_PERMISSIONS_EXTERNAL_STORAGE = 200;
    protected static final int GALLERY_PICTURE=0, CAMERA_REQUEST=1;
    static ImageView forEverywhereImage;
    Toolbar toolbar;
    ImageView image0, image1, image2, image3, image4, image5, image6, image7, image8, image9, image10, image11, image12;
    private Intent pictureActionIntent = null;
    Bitmap bitmap;
    String selectedImagePath, encodedBase64;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter_post);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Gugrify");
        setSupportActionBar(toolbar);

        checkIfExternalStoragePresent();
        languages = new ArrayList<>();
        categories = new ArrayList<>();
        twelveImages = findViewById(R.id.imageListTableLayout);
        twelveTexts = findViewById(R.id.editTextListLinearLayout);
        fontRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

        Button upload0 = findViewById(R.id.uploadImage0);
        Button upload1 = findViewById(R.id.uploadImage1);
        Button upload2 = findViewById(R.id.uploadImage2);
        Button upload3 = findViewById(R.id.uploadImage3);
        Button upload4 = findViewById(R.id.uploadImage4);
        Button upload5 = findViewById(R.id.uploadImage5);
        Button upload6 = findViewById(R.id.uploadImage6);
        Button upload7 = findViewById(R.id.uploadImage7);
        Button upload8 = findViewById(R.id.uploadImage8);
        Button upload9 = findViewById(R.id.uploadImage9);
        Button upload10 = findViewById(R.id.uploadImage10);
        Button upload11 = findViewById(R.id.uploadImage11);
        Button upload12 = findViewById(R.id.uploadImage12);

        upload0.setTypeface(fontRegular);
        upload1.setTypeface(fontRegular);
        upload2.setTypeface(fontRegular);
        upload3.setTypeface(fontRegular);
        upload4.setTypeface(fontRegular);
        upload5.setTypeface(fontRegular);
        upload6.setTypeface(fontRegular);
        upload7.setTypeface(fontRegular);
        upload8.setTypeface(fontRegular);
        upload9.setTypeface(fontRegular);
        upload10.setTypeface(fontRegular);
        upload11.setTypeface(fontRegular);
        upload12.setTypeface(fontRegular);

        upload0.setOnClickListener(this);
        upload1.setOnClickListener(this);
        upload2.setOnClickListener(this);
        upload3.setOnClickListener(this);
        upload4.setOnClickListener(this);
        upload5.setOnClickListener(this);
        upload6.setOnClickListener(this);
        upload7.setOnClickListener(this);
        upload8.setOnClickListener(this);
        upload9.setOnClickListener(this);
        upload10.setOnClickListener(this);
        upload11.setOnClickListener(this);
        upload12.setOnClickListener(this);

        image0 = findViewById(R.id.news_main_image);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);
        image9 = findViewById(R.id.image9);
        image10 = findViewById(R.id.image10);
        image11 = findViewById(R.id.image11);
        image12 = findViewById(R.id.image12);

        upload0.setTag(image0);
        upload1.setTag(image1);
        upload2.setTag(image2);
        upload3.setTag(image3);
        upload4.setTag(image4);
        upload5.setTag(image5);
        upload6.setTag(image6);
        upload7.setTag(image7);
        upload8.setTag(image8);
        upload9.setTag(image9);
        upload10.setTag(image10);
        upload11.setTag(image11);
        upload12.setTag(image12);

        //Spinner for input type
        Spinner spinner = findViewById(R.id.newsTypeSelectionSpinner);
        languages.add("Texts");
        languages.add("Images");
        languages.add("Select");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages){

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
                  if(languages.get(i).equals("Select")){
                      twelveImages.setVisibility(View.GONE);
                      twelveTexts.setVisibility(View.GONE);
                  }
                  else if(languages.get(i).equals("Texts")){
                      twelveImages.setVisibility(View.GONE);
                      twelveTexts.setVisibility(View.VISIBLE);
                  }
                  else {
                      twelveImages.setVisibility(View.VISIBLE);
                      twelveTexts.setVisibility(View.GONE);
                  }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner for category (has to be fetched from API)
        Spinner categoriesSpinner = findViewById(R.id.categorySelectionSpinner);
        categories.add("Sports");
        categories.add("Business");
        categories.add("Arts and Culture");
        categories.add("Entertainment");
        categories.add("Education");
        categories.add("Select");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories){

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
                if(categories.get(i).equals("Select")){

                }
                else {
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
        forEverywhereImage = (ImageView) view.getTag();
        startDialog();
    }

    void startDialog(){
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
                Bitmap convertedImage = getResizedBitmap(thumbnail, 400);
                forEverywhereImage.setImageBitmap(convertedImage);
                if(checkIfExternalStoragePresent())
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
                    Bitmap converetdImage = getResizedBitmap(bitmap, 400);
//                    String path = saveImage(bitmap);
                    Toast.makeText(ReporterPostActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    forEverywhereImage.setImageBitmap(converetdImage);

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

        Log.d("Original pixels are", String.valueOf(width)+" "+ String.valueOf(height));

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        Log.d("New pixels are", String.valueOf(width)+" "+ String.valueOf(height));
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public boolean checkIfExternalStoragePresent() {
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();

        if(isSDSupportedDevice && isSDPresent)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,System.currentTimeMillis()+"profile.jpg");

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

}
