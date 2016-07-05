package com.example.android.insignia2k16;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Dell Inspiron on 6/27/2016.
 */
public class GridAdapter extends BaseAdapter {
    Context context;
    public int images[]={R.drawable.im,R.drawable.a2,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im,R.drawable.im};
    public GridAdapter(Gallery mainActivity) {
        context=mainActivity;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ImageView iview;
        if(view==null)
        {
            iview=new ImageView(context);
            iview.setLayoutParams(new GridView.LayoutParams(240,240));
            iview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iview.setPadding(5,5,5,5);
        }
        else
        {
            iview=(ImageView)view;
        }
        iview.setImageResource(images[position]);
        return iview;
    }
}
