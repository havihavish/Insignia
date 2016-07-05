package com.example.android.insignia2k16;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    ImageView mImageView;
    TextView mTextView,mEventName;
    ImageView mFab;
    Button mRegister_button,agree_btn,disagree_btn;
    RelativeLayout mRelativeLayout;
    boolean flag = true;
    float pixelDensity;
    Animation alphaAppear,alphaDissapear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getBackground().setAlpha(0);
        setSupportActionBar(toolbar);

        mImageView = (ImageView) findViewById(R.id.detail_imageView);
        mFab = (ImageView) findViewById(R.id.detail_fab);
        mTextView = (TextView) findViewById(R.id.detail_textView);
        mRegister_button = (Button)findViewById(R.id.detail_register_button);
        mRelativeLayout = (RelativeLayout)findViewById(R.id.abc);
        disagree_btn = (Button)findViewById(R.id.button_disAgree);

        alphaAppear = AnimationUtils.loadAnimation(this,R.anim.alpha_anim);
        alphaDissapear = AnimationUtils.loadAnimation(this,R.anim.alpha_disappear);

        pixelDensity = getResources().getDisplayMetrics().density;

        final int position = getIntent().getIntExtra("p",0);

        mRegister_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration(position);
            }
        });

        mImageView.setImageResource(Constants.mEvents_posters[position]);
        mTextView.setText(Constants.mEvents_names[position]);
        Bitmap bitmap = getReducedBitmap(position);
        chooseColor(bitmap);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularReveal(mRelativeLayout);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void registration(int position){

        final Dialog dialog = new Dialog(DetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.requirementsdialog);
        agree_btn = (Button)dialog.findViewById(R.id.button_agree);
        disagree_btn = (Button)dialog.findViewById(R.id.button_disAgree);
        mEventName = (TextView)dialog.findViewById(R.id.dialog_event_title);
        mEventName.setText(Constants.mEvents_names[position]);
        //navigate to registration activity
        if (agree_btn!=null)
            agree_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailActivity.this,Registration.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });
        disagree_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void circularReveal(View selectedView) {
        View myView = selectedView;

        // get the center for the clipping circle
        int cx = myView.getRight() -30;
        int cy = myView.getBottom() -60;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        Animator anim =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            if (flag){
                anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius).setDuration(500);
                mRelativeLayout.setVisibility(View.VISIBLE);
                anim.start();
                flag = false;
            }else {
                anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, finalRadius, 0).setDuration(500);
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRelativeLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                anim.start();
                flag = true;
            }
        }


    }

    private Bitmap getReducedBitmap(int albumArtResId) {
        // reduce image size in memory to avoid memory errors
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 8;
        return BitmapFactory.decodeResource(getResources(), Constants.mEvents_posters[albumArtResId], options);
    }

    private void chooseColor(Bitmap b) {

        Palette.generateAsync(b, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getVibrantSwatch();
                if (swatch!=null){
                    mTextView.setBackgroundColor(swatch.getRgb());
                    mTextView.setTextColor(swatch.getTitleTextColor());
                    mRegister_button.setBackgroundColor(swatch.getRgb());
                }

            }
        });
    }
}
