package com.example.android.insignia2k16.Instafeed;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.android.insignia2k16.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Instafeed extends AppCompatActivity {

    HttpURLConnection connection;
    ArrayList<String> list;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instafeed);
        list=new ArrayList<String>();
        MyFetchTask myFetchTask=new MyFetchTask();
        myFetchTask.execute();
        setTitle("Instafeed");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    class CustomAdapter extends ArrayAdapter {

        Context context;
        private LayoutInflater inflater;
        public CustomAdapter(Context context, int resource, List objects) {
            super(context, resource, objects);
            this.context=context;
            inflater = LayoutInflater.from(context);
        }

        public CustomAdapter(Context context, int resource, Object[] objects) {
            super(context, resource, objects);
            this.context=context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if (null == convertView) {
                convertView = inflater.inflate(R.layout.card_row, parent, false);
            }

            YoYo.with(Techniques.FlipInX)
                    .duration(700)
                    .playOn(convertView.findViewById(R.id.cardView));
            Log.d("aaaaaaa",list.get(position));
            Picasso
                    .with(this.context)
                    .load(Uri.parse(list.get(position)))
                    .fit() // will explain later
                    .into((ImageView) convertView.findViewById(R.id.img3));

            return convertView;
        }


    }

    class MyFetchTask extends AsyncTask<String ,String ,String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            listView=(ListView)findViewById(R.id.listView);
            CustomAdapter adapter=new CustomAdapter(Instafeed.this,R.layout.card_row,list);
            listView.setAdapter(adapter);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url=new URL("https://www.instagram.com/terratechnica/media/");
                connection=(HttpURLConnection)url.openConnection();
                connection.connect();

                InputStream inputStream=connection.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer=new StringBuffer();
                String s=null;
                while((s=br.readLine())!=null){
                    stringBuffer.append(s);
                }
                Log.d("xxxxxxxxx",stringBuffer+"");
                String img="";
                JSONObject object=new JSONObject(stringBuffer.toString());
                Log.e("XXXXX",object.toString());
                JSONArray array=object.getJSONArray("items");
                for(int i=0;i<array.length();i++){
                    JSONObject jsonObject=array.getJSONObject(i);
                    JSONObject jsonObject1=jsonObject.getJSONObject("images");
                    JSONObject myJson=jsonObject1.getJSONObject("standard_resolution");
                    img=myJson.getString("url");
                    list.add(img);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
