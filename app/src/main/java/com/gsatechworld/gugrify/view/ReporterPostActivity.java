package com.gsatechworld.gugrify.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import java.util.ArrayList;
import java.util.List;

public class ReporterPostActivity extends AppCompatActivity implements View.OnClickListener{
    List<String> languages;
    TableLayout twelveImages;
    LinearLayout twelveTexts;
    Typeface fontRegular;
    protected static final int GALLERY_PICTURE=0, CAMERA_REQUEST=1;
    static ImageView forEverywhereImage;
    ImageView image0, image1, image2, image3, image4, image5, image6, image7, image8, image9, image10, image11, image12;
    private Intent pictureActionIntent = null;
    Bitmap bitmap;
    String selectedImagePath, encodedBase64;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter_post);
        languages = new ArrayList<>();
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

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, CAMERA_REQUEST);

                    }
                });
        myAlertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        bitmap = null;
        selectedImagePath = null;

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {

                Toast.makeText(getBaseContext(),

                        "Error while capturing image", Toast.LENGTH_LONG)

                        .show();

                return;

            }

            try {

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);



                forEverywhereImage.setImageBitmap(bitmap);
                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            if (data != null) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage, filePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                selectedImagePath = c.getString(columnIndex);
                c.close();

                createDirectory(selectedImagePath);


                // preview image

//                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);

                int random = (int) (Math.random() * (55000 - 1));
                String destFile = Environment.getExternalStorageDirectory().getAbsolutePath() + random + ".jpg";

                Log.d(ReporterPostActivity.class.getSimpleName(), "destFile:" + destFile);
                copy(new File(selectedImagePath), new File(destFile));
                Glide.with(ReporterPostActivity.this)
                        .load(destFile)
                        .asBitmap()
                        .placeholder(R.drawable.user_profile)
                        .into(forEverywhereImage.setImageBitmap(bitmap));

                forEverywhereImage.setImageBitmap(bitmap);

            } else {
                Toast.makeText(getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
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

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public void createDirectory(String path){
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/gugrify");
        if(dir.exists() == false){
            dir.mkdirs();
        }
    }

    public void copy(File src, File dst){
        try {

            if (dst.exists()) {
                dst.delete();
            }
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
//            Log.d(Ut-.class.getSimpleName(), "file copied");
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
