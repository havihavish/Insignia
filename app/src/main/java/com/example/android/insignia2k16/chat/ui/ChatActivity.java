package com.example.android.insignia2k16.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android.insignia2k16.Constants;
import com.example.android.insignia2k16.R;
import com.example.android.insignia2k16.chat.model.Messages;
import com.example.android.insignia2k16.chat.ui.Adapter.MyFirebaseAdapter;
import com.example.android.insignia2k16.chat.ui.login.LoginActivity;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class ChatActivity extends AppCompatActivity {

    Firebase mRef;
    MyFirebaseAdapter mFirebaseAdapter;
    ListView mListView;
    EditText messageText;
    Button mSend;
    Firebase messageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRef = new Firebase(Constants.FIREBASE_BASE_URL);
        mListView = (ListView)findViewById(R.id.chat_listView);
        messageText = (EditText)findViewById(R.id.message_text);
        mSend = (Button)findViewById(R.id.send_button);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        mRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData == null){
                    Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        messageRef = mRef.child(Constants.MESSAGES);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //create firebase list adapter

        mFirebaseAdapter = new MyFirebaseAdapter(ChatActivity.this, Messages.class,R.layout.message_list_item,messageRef);

        mListView.setDivider(null);
        mListView.setDividerHeight(0);
        mListView.setAdapter(mFirebaseAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void sendMessage() {

        String message = messageText.getText().toString();
        String mUser = "Dummy user";

        messageText.setText("");

        Messages chat = new Messages(mUser,message);

        messageRef.push().setValue(chat);

    }

}
