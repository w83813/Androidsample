package com.example.miis200;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;

public class Addexamination_examination_viewphoto extends AppCompatActivity {

    PhotoView eye_Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexamination_examination_viewphoto);
        Intent intent_Get_image_path = getIntent();
        String ImagePath = intent_Get_image_path.getStringExtra("Image_path");
        System.out.println("IIIIIIIIIIIIIII" + ImagePath);
        eye_Image = (PhotoView)findViewById(R.id.eye_Image);
        eye_Image.setImageURI(Uri.parse(ImagePath));
    }

    public void onBackPressed() {
        finish();
    }
}
