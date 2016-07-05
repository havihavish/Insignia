package com.example.android.insignia2k16.chat;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.firebase.client.Firebase;

/**
 * Created by surya on 04-07-2016.
 */
public class ChatApllication  extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //set up firebase
        Firebase.setAndroidContext(this);
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
