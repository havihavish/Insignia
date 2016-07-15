package com.example.android.insignia2k16.chat.model;

import com.example.android.insignia2k16.Constants;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

/**
 * Created by surya on 04-07-2016.
 */
public class Messages {
    String user;
    String message;
    String photoUrl;
    HashMap<String,Object> timestampSent;

    public Messages() {
    }

    public Messages(String user, String message) {
        this.user = user;
        this.message = message;
        HashMap<String,Object> timestampCreated = new HashMap<>();
        timestampCreated.put(Constants.TIMESTAMP, ServerValue.TIMESTAMP);
        this.timestampSent = timestampCreated;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<String, Object> getTimestampSent() {
        return timestampSent;
    }

    @Exclude
    public long getTimestampSentLong() {
        return (long) timestampSent.get(Constants.TIMESTAMP);
    }

}
