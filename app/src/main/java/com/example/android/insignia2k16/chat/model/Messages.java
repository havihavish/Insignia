package com.example.android.insignia2k16.chat.model;

import com.example.android.insignia2k16.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;

import java.util.HashMap;

/**
 * Created by surya on 04-07-2016.
 */
public class Messages {
    String user;
    String message;
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

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<String, Object> getTimestampSent() {
        return timestampSent;
    }

    @JsonIgnore
    public long getTimestampSentLong() {
        return (long) timestampSent.get(Constants.TIMESTAMP);
    }

}
