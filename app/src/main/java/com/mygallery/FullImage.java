package com.mygallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class FullImage extends AppCompatActivity {
    ImageView full_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        full_image = (ImageView) findViewById(R.id.full_image);
        String data = getIntent().getExtras().getString("img");
        full_image.setImageURI(Uri.parse(data));
    }
}
