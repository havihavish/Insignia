package com.example.android.insignia2k16;

/**
 * Created by surya on 22-06-2016.
 */
public class Constants {
    public static final String TIMESTAMP = "timestamp";
    public static final String USERS_DETAILS = "users details";
    public static String USERNAME = "USERNAME";
    public static final String EMAIL = "EMAIL";
    public static boolean FLAG = false;
    public static String[] mEvents_names = {"NITD MUN '16","GHOST WRITING","TUSSLE OF TONGUES","RAGEPICKERS PURSUIT","QUIZZING ","SPOKE A SPELL","POEM WRITING","TREASURE HUNT"};
    public static Integer[] mEvents_posters = {R.drawable.poster1,R.drawable.poster2,R.drawable.poster3,R.drawable.poster4,
            R.drawable.poster5,R.drawable.poster6,R.drawable.poster7,R.drawable.poster8};

    //firebase base url
    public static final String FIREBASE_BASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;

    //tree roots
    public static final String USERS = "users";
    public static final String MESSAGES = "messages";

}
