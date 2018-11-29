package com.bichi.storeimageindb;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    EditText edtName;
    Button btnChose,btnAdd,btnList;
    ImageView imageView;
    final int REQUEST_CODE_GALLERY = 999;
    Bitmap thumbnail;
    RoundedImageView roundedImageView;
    public static DatabaseHandler databaseHandler;
    static String TAG = "MainActivity";
    byte[] inputData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        databaseHandler = new DatabaseHandler(this);

        btnChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    long l=databaseHandler.insertData(
                            edtName.getText().toString().trim(),
                            /*inputData*/
                            imageBiewToByte()
                    );
                    Log.d(TAG, "onClick: l value is "+l);
                    if(l>=1){
                        Toast.makeText(MainActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                        edtName.setText("");
                        imageView.setImageResource(R.mipmap.ic_launcher);
                    }else {
                        Toast.makeText(MainActivity.this, "Error in inserting db", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Exception in inserting database", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FoodList.class);
                startActivity(intent);
            }
        });
    }
    private  byte[] imageBiewToByte() {
        Log.d(TAG, "imageBiewToByte: ");
        //Bitmap bitmap =((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new   Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);

            }else{
                Toast.makeText(this, "you dont have permission", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && data!=null){
            Uri uri = data.getData();
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
            /*try {
                *//*InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);*//*
                Uri selectedImage = data.getData();

                String[] filePath = { MediaStore.Images.Media.DATA };

                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                String picturePath = c.getString(columnIndex);

                c.close();

                thumbnail = BitmapFactory.decodeFile(picturePath);

                Log.w("path of image from", picturePath+"");

                //imageView.setImageBitmap(thumbnail);
                drawImage(imageView,thumbnail);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri resultUri = result.getUri();

            try {
                InputStream iStream = getContentResolver().openInputStream(resultUri);
                //inputData = getBytes(iStream); //is used to store byte array data to db
                InputStream inputStream = getContentResolver().openInputStream(resultUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                drawImage(imageView,bitmap);
                thumbnail=bitmap;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init(){
        edtName = findViewById(R.id.editText);
        btnChose = findViewById(R.id.button);
        btnAdd = findViewById(R.id.button2);
        btnList = findViewById(R.id.button3);
        imageView = findViewById(R.id.imageView);
    }
    public void drawImage(ImageView imageView, Bitmap bm)
    {
        roundedImageView = new RoundedImageView(this);
        Bitmap conv_bm = roundedImageView.getCroppedBitmap(bm, 250, 7, Color.WHITE);
        imageView.setImageBitmap(conv_bm);
    }
    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
