package com.example.android.insignia2k16;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class FullImageActivity extends AppCompatActivity {
    private int images[]={R.drawable.im,R.drawable.a2,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        Intent i=getIntent();
        int position=i.getExtras().getInt("id");
        ImageView img=(ImageView)findViewById(R.id.imageView);
        img.setImageResource(images[position]);

    }

}
