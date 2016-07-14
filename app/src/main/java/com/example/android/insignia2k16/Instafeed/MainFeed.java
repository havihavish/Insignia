package com.example.android.insignia2k16.Instafeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.insignia2k16.R;

public class MainFeed extends AppCompatActivity {

    Button instalogin;
    TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);
        instalogin=(Button)findViewById(R.id.insta_login);
        skip=(TextView)findViewById(R.id.skip_insta);

        instalogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(MainFeed.this,InstaLoginMainActivity.class);
                startActivity(intent);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(MainFeed.this,Instafeed.class);
                startActivity(intent);
            }
        });
    }
}
