package com.example.android.insignia2k16.chat.ui.Adapter;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.insignia2k16.R;
import com.example.android.insignia2k16.chat.model.Messages;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by surya on 04-07-2016.
 */
public class MyFirebaseAdapter  extends FirebaseListAdapter<Messages>{

    /**
     * Public constructor that initializes private instance variables when adapter is created
     */
    public MyFirebaseAdapter(Activity activity, Class<Messages> modelClass, int modelLayout,
                             Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    /**
     * Protected method that populates the view attached to the adapter (list_view_active_lists)
     * with items inflated from single_active_list.xml
     * populateView also handles data changes and updates the listView accordingly
     */
    @Override
    protected void populateView(View view, Messages list) {

        /**
         * Grab the needed Textivews and strings
         *
         */
        RelativeLayout layout = (RelativeLayout)view.findViewById(R.id.chat_layout);
        TextView username = (TextView)view.findViewById(R.id.sent_user);
        TextView usermessage = (TextView)view.findViewById(R.id.users_message);
        TextView messagestamp = (TextView)view.findViewById(R.id.message_timestamp);

        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        //TODO change 9 patch image image to green if current user
        usermessage.setText(list.getMessage());
        username.setText(list.getUser());
        messagestamp.setText(dateFormat.format(new Date(list.getTimestampSentLong())));


    }


}
