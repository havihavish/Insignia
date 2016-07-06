package com.example.android.insignia2k16.Registration;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


import com.example.android.insignia2k16.R;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import static java.lang.Thread.sleep;

public class Registration extends ActionBarActivity {


    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    //URL derived from form URL
    public static final String URL="https://docs.google.com/forms/d/1TnlE09UMKv9rv7m9vf53p87mZk8XgP7s6L5GQv1gl34/formResponse";
    //input element ids found from the live form page
    public static final String sname="entry.697119795";
    public static final String semail="entry.100753150";
    public static final String smobile="entry.1345272859";
    public static final String scollege="entry.1526759000";
    //private final Context context;
    private EditText etname,etmail,etno,etcollege;
    private ImageView iv;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Registration");
        //save the activity in a context variable to be used afterwards
        //context =this;

        //Get references to UI elements in the layout
        final Button sendButton = (Button)findViewById(R.id.button3);
        etname = (EditText)findViewById(R.id.name);
        etmail = (EditText)findViewById(R.id.email);
        etno = (EditText)findViewById(R.id.mobile);
        etcollege = (EditText)findViewById(R.id.college);
        iv=(ImageView)findViewById(R.id.iViewInsignia);
        tv=(TextView)findViewById(R.id.tViewIn);






        iv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Make sure all the fields are filled with values
                /*AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
                anim.setDuration(500);
                anim.setRepeatCount(0);

                //anim.setRepeatMode(Animation.REVERSE);

                iv.startAnimation(anim);
                //iv.setVisibility(View.INVISIBLE);*/

                sendButton.setVisibility(View.VISIBLE);
                TranslateAnimation slide=new TranslateAnimation(0,0,200,0);
                slide.setDuration(1000);
                slide.setFillAfter(true);
                sendButton.startAnimation(slide);

                etname.setVisibility(View.VISIBLE);
                TranslateAnimation edit1=new TranslateAnimation(100,0,0,0);
                edit1.setDuration(750);
                edit1.setFillAfter(true);
                etname.startAnimation(edit1);

                etmail.setVisibility(View.VISIBLE);
                TranslateAnimation edit2=new TranslateAnimation(100,0,0,0);
                edit2.setDuration(1000);
                edit2.setFillAfter(true);
                etmail.startAnimation(edit2);

                etno.setVisibility(View.VISIBLE);
                TranslateAnimation edit3=new TranslateAnimation(100,0,0,0);
                edit3.setDuration(1250);
                edit3.setFillAfter(true);
                etno.startAnimation(edit3);

                etcollege.setVisibility(View.VISIBLE);
                TranslateAnimation edit4=new TranslateAnimation(100,0,0,0);
                edit4.setDuration(1500);
                edit4.setFillAfter(true);
                etcollege.startAnimation(edit4);
                iv.setClickable(false);
                tv.setVisibility(View.INVISIBLE);


            }
        });



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(TextUtils.isEmpty(etname.getText().toString()) ||
                        TextUtils.isEmpty(etmail.getText().toString()) ||
                        TextUtils.isEmpty(etno.getText().toString()) ||
                        TextUtils.isEmpty(etcollege.getText().toString())
                        )

                {
                    Toast.makeText(Registration.this,"All fields are mandatory.",Toast.LENGTH_LONG).show();
                    return;
                }

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(etmail.getText().toString()).matches())
                {
                    Toast.makeText(Registration.this,"Please enter a valid email.",Toast.LENGTH_LONG).show();
                    return;
                }


                PostDataTask postDataTask = new PostDataTask();


                postDataTask.execute(URL,etname.getText().toString(),
                        etmail.getText().toString(),
                        etno.getText().toString(), etno.getText().toString()
                );
            }
        });
    }


    private class PostDataTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... contactData) {
            Boolean result = true;
            String url = contactData[0];
            String name = contactData[1];
            String email = contactData[2];
            String mobile = contactData[3];
            String college = contactData[4];
            String postBody="";

            try {

                postBody = sname+"=" + URLEncoder.encode(name,"UTF-8") +
                        "&" + semail + "=" + URLEncoder.encode(email,"UTF-8") +
                        "&" + smobile + "=" + URLEncoder.encode(mobile,"UTF-8") +
                        "&" + scollege + "=" + URLEncoder.encode(college,"UTF-8");
            } catch (UnsupportedEncodingException ex) {
                result=false;
            }



            try{

                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
            }catch (IOException exception){
                result=false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result){
            Toast.makeText(Registration.this,result?"Successfully registered! Thank You!":"There was some error in sending message. Please try again after some time.",Toast.LENGTH_LONG).show();
            finish();
        }

    }
}
