package com.example.android.insignia2k16.Instafeed;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vengalrao on 10-07-2016.
 */
public class InstagramSession {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private static final String SHARED = "Instagram_Preferences";
    private static final String API_USERNAME = "username";
    private static final String API_ID = "id";
    private static final String API_NAME = "name";
    private static final String API_ACCESS_TOKEN = "access_token";

    public InstagramSession(Context context) {
        sharedPref = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public List<String> giveList(){
        List<String> list=new ArrayList();
        int size=sharedPref.getInt("size",0);
        Log.d("yyyy",list.get(0));
        for(int i=0;i<size;i++){
            list.add(sharedPref.getString("listItem"+i,null));
        }
        return list;
    }

    public void storeAccessToken(String accessToken, String id, String username, String name) {
        editor.putString(API_ID, id);
        editor.putString(API_NAME, name);
        editor.putString(API_ACCESS_TOKEN, accessToken);
        editor.putString(API_USERNAME, username);
        editor.commit();
    }
    public void storeList( List list){
       // Log.d("yyyy",(String) list.get(0));
        editor.putInt("size",list.size());
        for(int i=0;i<list.size();i++)
        editor.putString("listItem"+i,(String)list.get(i));
        editor.commit();
    }
    public void storeAccessToken(String accessToken) {
        editor.putString(API_ACCESS_TOKEN, accessToken);
        editor.commit();
    }

    /**
     * Reset access token and user name
     */
    public void resetAccessToken() {
        editor.putString(API_ID, null);
        editor.putString(API_NAME, null);
        editor.putString(API_ACCESS_TOKEN, null);
        editor.putString(API_USERNAME, null);
        editor.commit();
    }

    /**
     * Get user name
     *
     * @return User name
     */
    public String getUsername() {
        return sharedPref.getString(API_USERNAME, null);
    }
    /**
     *
     * @return
     */
    public String getId() {
        return sharedPref.getString(API_ID, null);
    }
    /**
     *
     * @return
     */
    public String getName() {
        return sharedPref.getString(API_NAME, null);
    }

    /**
     * Get access token
     *
     * @return Access token
     */
    public String getAccessToken() {
        return sharedPref.getString(API_ACCESS_TOKEN, null);
    }
}
