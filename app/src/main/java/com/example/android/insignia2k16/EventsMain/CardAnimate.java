package com.example.android.insignia2k16.EventsMain;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import com.example.android.insignia2k16.EventsMain.MyAdapter;

/**
 * Created by surya on 25-06-2016.
 */
public class CardAnimate {
    public static void animater(MyAdapter.ViewHolder holder, boolean b) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(holder.itemView,"translationY", b ? 300 : -300 ,0);
        animator.setDuration(300);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator);
        set.start();



    }
}
