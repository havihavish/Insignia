package com.example.android.insignia2k16.Instafeed;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.android.insignia2k16.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vengalrao on 10-07-2016.
 */
public class InstaLoginMainActivity extends AppCompatActivity {
    private InstagramApp mApp;
    private Button btnConnect, btnViewInfo, btnGetAllImages;
    private InstagramSession mSession;
    List<String> list=new ArrayList();
    List<Boolean> liked=new ArrayList<>();
    ListView listView;
    ImageView likes,comment,uparrow;
    Button post;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act);


        mApp = new InstagramApp(this, ApplicationData.CLIENT_ID,
                ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);

        mApp.setListener(new InstagramApp.OAuthAuthenticationListener() {

            @Override
            public void onSuccess() {
                   MyFetch myFetch=new MyFetch();
                   myFetch.execute();
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(InstaLoginMainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });



    }

    class MyFetch extends AsyncTask<String,String,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            liked=mApp.getWhetherLiked();
            list=mApp.getList();
            listView=(ListView)findViewById(R.id.lViewInstaSession);
            CustomAdapter adapter=new CustomAdapter(InstaLoginMainActivity.this,R.layout.card_row_session,list,liked);
            listView.setAdapter(adapter);
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }

    class CustomAdapter extends ArrayAdapter {

        Context context;
        private LayoutInflater inflater;
        List<Boolean> check;
        public CustomAdapter(Context context, int resource, List objects,List like) {
            super(context, resource, objects);
            this.context=context;
            inflater = LayoutInflater.from(context);
            check=like;
        }

        public CustomAdapter(Context context, int resource, Object[] objects) {
            super(context, resource, objects);
            this.context=context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

             ViewHolder holder=new ViewHolder();

                  Log.d("number","num:"+position+" "+liked.get(position)+" "+list.get(position));
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.card_row_session, parent, false);
                holder.like=(ImageView)convertView.findViewById(R.id.likes);
                holder.comment_Image=(ImageView)convertView.findViewById(R.id.comment);
                holder.comment=(EditText)convertView.findViewById(R.id.editText_comment);
                holder.post=(Button)convertView.findViewById(R.id.post);
                holder.uparrow=(ImageView) convertView.findViewById(R.id.uparrow);
                holder.first=(LinearLayout)convertView.findViewById(R.id.options);
                holder.second=(LinearLayout)convertView.findViewById(R.id.lLayout_main);
                holder.share=(ImageView)convertView.findViewById(R.id.share);
                holder.mainImage=(ImageView)convertView.findViewById(R.id.img3);
                convertView.setTag(holder);
            }else{
                holder=(ViewHolder)convertView.getTag();
            }

            final ViewHolder inner=holder;
            if(check.get(position)==true){
                holder.like.setImageResource(R.drawable.like_red);
            }else{
                holder.first.setVisibility(View.VISIBLE);
                holder.second.setVisibility(View.GONE);
                holder.like.setImageResource(R.drawable.like_black);
                holder.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mApp.likes(position);
                        inner.like.setImageResource(R.drawable.like_red);
                    }
                });
                final View finalConvertView = convertView;
                holder.comment_Image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inner.first.setVisibility(View.GONE);
                        inner.second.setVisibility(View.VISIBLE);

                    }
                });
                holder.post=(Button)convertView.findViewById(R.id.post);


                holder.post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String text=inner.comment.getText().toString();
                        if(!text.equals("")){
                            inner.comment.setText("");
                            mApp.comments(position,text);
                        }

                    }
                });

                holder.uparrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(inner.comment.length()>0){
                            inner.comment.setText("");
                        }
                        inner.second.setVisibility(View.GONE);
                        inner.first.setVisibility(View.VISIBLE);
                    }
                });

                holder.comment.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Log.d("xxxxxxx","watcher");
                        if (s.length()!=0) {
                            inner.post.setClickable(true);
                            inner.post.setEnabled(true);
                        }
                        if (s.length()==0){
                            inner.post.setEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }


//            YoYo.with(Techniques.FlipInX)
//                    .duration(700)
//                    .playOn(convertView.findViewById(R.id.cardView));
            Picasso
                    .with(this.context)
                    .load(Uri.parse(list.get(position)))
                    .fit() // will explain later
                    .into((ImageView) convertView.findViewById(R.id.img3));

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Drawable drawable=inner.mainImage.getDrawable();
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
                    Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                    int permissionCheck = ContextCompat.checkSelfPermission(InstaLoginMainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                InstaLoginMainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    }

                    String path= MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"img_"+position,null);

                    Uri uri=Uri.parse(path);
                    if(uri!=null){
                        Intent shareIntent=new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
                        shareIntent.setType("image/*");
                        startActivity(Intent.createChooser(shareIntent,"Share"));
                    }else{
                        Toast.makeText(InstaLoginMainActivity.this,"failed to share",Toast.LENGTH_LONG);
                    }
                }
            });

            return convertView;
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    static class ViewHolder{
        EditText comment;
        Button post;
        ImageView like;
        ImageView comment_Image;
        ImageView share;
        ImageView uparrow;
        LinearLayout first;
        LinearLayout second;
        ImageView mainImage;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("xxxxxxxxx","destroy");
    }
    // OAuthAuthenticationListener listener ;


    private void connectOrDisconnectUser() {
        if (mApp.hasAccessToken()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(
                    InstaLoginMainActivity.this);
            builder.setMessage("Disconnect from Instagram?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    mApp.resetAccessToken();
// btnConnect.setVisibility(View.VISIBLE);
                                    //llAfterLoginView.setVisibility(View.GONE);
                                    btnConnect.setText("Connect");
// tvSummary.setText("Not connected");
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });
            final AlertDialog alert = builder.create();
            alert.show();
        } else {
            mApp.authorize();
        }
    }

}
