package com.example.android.insignia2k16;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    ImageView mImageView;
    TextView mTextView;
    ImageView mFab;
    Button mRegister_button;
    RelativeLayout mRelativeLayout;
    boolean flag = true;
    float pixelDensity;
    int width,height,finalRadius;
    int cx,cy;
    Animation alphaAppear,alphaDissapear;
    ImageView closeButton;

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
        closeButton = (ImageView) findViewById(R.id.close_button);

        alphaAppear = AnimationUtils.loadAnimation(this,R.anim.alpha_anim);
        alphaDissapear = AnimationUtils.loadAnimation(this,R.anim.alpha_disappear);

        pixelDensity = getResources().getDisplayMetrics().density;

        mRegister_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration();
            }
        });
        int position = getIntent().getIntExtra("p",0);

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
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeRevealView(mRelativeLayout);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void closeRevealView(RelativeLayout layout) {
        Log.e("XXXX","Enter");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Log.e("XXX","Enter ANma");
            Animator anim = ViewAnimationUtils.createCircularReveal(mRelativeLayout,width/2,height/2,finalRadius,28 * pixelDensity );
            anim.setDuration(350);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mRelativeLayout.setVisibility(View.GONE);
                    mFab.setVisibility(View.VISIBLE);
                    int x=cx = (int) (cx + ((16 * pixelDensity) + (28 * pixelDensity)));

                    mFab.animate()
                            .translationX(x)
                            .translationY(cy)
                            .setDuration(200);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }
                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            anim.start();
        }
    }

    private void circularReveal(RelativeLayout layout) {

        final View myView = layout;

        width = mImageView.getWidth();
        height = mImageView.getHeight();

        cx = mImageView.getWidth()/2;
        cy = mImageView.getHeight()/2;

        finalRadius = (int) Math.hypot(cx,cy);

        cx = (int) (cx - ((16 * pixelDensity) + (28 * pixelDensity)));

        mFab.animate()
                .translationX(-cx)
                .translationY(-cy)
                .setDuration(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            Animator anim = ViewAnimationUtils.createCircularReveal(myView,width/2,height/2,28 * pixelDensity,finalRadius);
                            anim.setDuration(350);
                            anim.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                        myView.setVisibility(View.VISIBLE);
                              }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                            myView.setVisibility(View.VISIBLE);
                            mFab.setVisibility(View.GONE);
                            anim.start();
                        }

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

    }

    public void registration(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Requirements")
                .setMessage("Condition a" + "\n" + "Condition b");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(DetailActivity.this,Registration.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
//
//    private void circularReveal(View selectedView) {
//        View myView = selectedView;
//
//        // get the center for the clipping circle
//        int cx = myView.getRight() -30;
//        int cy = myView.getBottom() -60;
//
//        // get the final radius for the clipping circle
//        float finalRadius = (float) Math.hypot(cx, cy);
//
//        // create the animator for this view (the start radius is zero)
//        Animator anim =
//                null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            if (flag){
//                anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius).setDuration(500);
//                mRelativeLayout.setVisibility(View.VISIBLE);
//                anim.start();
//                flag = false;
//            }else {
//                anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, finalRadius, 0).setDuration(500);
//                anim.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        mRelativeLayout.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//                anim.start();
//                flag = true;
//            }
//        }
//
//
//    }

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
