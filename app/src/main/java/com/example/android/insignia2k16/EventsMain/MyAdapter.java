package com.example.android.insignia2k16.EventsMain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.insignia2k16.Constants;
import com.example.android.insignia2k16.R;

/**
 * Created by surya on 22-06-2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context mContext;
    LayoutInflater inflater;
    OnItemClickListener mItemClickListener;

    int prevPosition = 0;

    public MyAdapter(Context c) {
        mContext = c;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = inflater
                .inflate(R.layout.recycler_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextView.setText(Constants.mEvents_names[position]);
        holder.mImageView.setImageResource(Constants.mEvents_posters[position]);

//        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),Constants.mEvents_posters[position]);
//        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                    Palette.Swatch swatch = palette.getVibrantSwatch();
//                    if (swatch!=null){
//                        holder.mTextView.setBackgroundColor(swatch.getRgb());
//                    }
//
//            }
//        });

        if (position > prevPosition){
            //scroll down
            CardAnimate.animater(holder,true);

        }else {
            //scroll up
            CardAnimate.animater(holder,false);

        }

        prevPosition = position;

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return Constants.mEvents_names.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextView;
        ImageView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.list_item_imageView);
            mTextView = (TextView) itemView.findViewById(R.id.list_item_textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener!=null){
                mItemClickListener.onItemClick(v,getPosition());
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }
}