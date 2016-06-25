package com.example.android.insignia2k16;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    ImageView mImageView;
    TextView mTextView;
    ImageView mFab;
    Button mRegister_button;
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
        int position = getIntent().getIntExtra("p",0);
        mImageView.setImageResource(Constants.mEvents_posters[position]);
        mTextView.setText(Constants.mEvents_names[position]);
        Bitmap bitmap = getReducedBitmap(position);
        chooseColor(bitmap);
        animate();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Bitmap getReducedBitmap(int albumArtResId) {
        // reduce image size in memory to avoid memory errors
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 8;
        return BitmapFactory.decodeResource(getResources(), Constants.mEvents_posters[albumArtResId], options);
    }

    private void chooseColor(Bitmap b) {

        Palette pallete = Palette.from(b).generate();

        int defaultPanelColor = 0xFF808080;
        mTextView.setBackgroundColor(pallete.getDarkVibrantColor(defaultPanelColor));
        mRegister_button.setBackgroundColor(pallete.getDarkVibrantColor(defaultPanelColor));
    }

    private void animate() {

        mFab.setScaleX(0);
        mFab.setScaleY(0);
        mFab.animate().scaleX(1).scaleY(1).setDuration(700).start();
//        Animator fabAnimate = AnimatorInflater.loadAnimator(this,R.animator.scale);
//        fabAnimate.setTarget(mFab);
//        AnimatorSet set = new AnimatorSet();
//
//        int start = mTextView.getTop();
//        int end = mTextView.getBottom();
//
//        ObjectAnimator titleAnimate = ObjectAnimator.ofInt(mTextView,"bottom",start,end);
//
////        titleAnimate.setInterpolator(new AccelerateInterpolator());
//        mTextView.setBottom(start);
//        set.playSequentially(fabAnimate,titleAnimate);
//        set.start();

    }

}
