package com.example.android.insignia2k16.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
    public static String mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRef = new Firebase(Constants.FIREBASE_BASE_URL);
        mListView = (ListView)findViewById(R.id.chat_listView);
        messageText = (EditText)findViewById(R.id.message_text);

        mUser = getUser();

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
        mListView.post(new Runnable() {
            @Override
            public void run() {
                mListView.setSelection(mListView.getCount()-1);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            mRef.unauth();
            Intent intent = new Intent(ChatActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendMessage() {

        String message = messageText.getText().toString();

        if (!message.equals("")){

            messageText.setText("");

            Messages chat = new Messages(mUser,message);

            messageRef.push().setValue(chat);
        }else {
            messageText.setError("Empty Message");
        }
    }

    private String getUser() {

        String mUser = (String) mRef.getAuth().getProviderData().get("email");

        for (int i=0;i<mUser.length();i++){
            if (mUser.charAt(i) == '@')
                return mUser.substring(0,i);
        }
        return null;
    }

}
