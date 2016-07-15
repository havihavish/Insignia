package com.example.android.insignia2k16.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.insignia2k16.Constants;
import com.example.android.insignia2k16.R;
import com.example.android.insignia2k16.chat.Auth.Login;
import com.example.android.insignia2k16.chat.model.Messages;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GlobalChat extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView user;
        public TextView mUser_message;
        public TextView mTimestamp;

        public MessageViewHolder(View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.sent_user);
            mUser_message = (TextView) itemView.findViewById(R.id.users_message);
            mTimestamp = (TextView) itemView.findViewById(R.id.message_timestamp);
        }
    }

    EditText mEditText;
    Button mSend_btn;
    RecyclerView mRecyclerView;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mReference;
    FirebaseUser mFirebaseUser;
    LinearLayoutManager mLayoutManager;
    String userName;
    String photoUrl;
    FirebaseRecyclerAdapter<Messages,MessageViewHolder> mFirebaseAdapter;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mEditText = (EditText)findViewById(R.id.message_text);
        mSend_btn = (Button) findViewById(R.id.send_button);
        mRecyclerView = (RecyclerView)findViewById(R.id.chat_recyclerView);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();
        
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null){
            Toast.makeText(GlobalChat.this, "null", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(GlobalChat.this,Login.class));
            finish();
            return;
        }

        if (Constants.FLAG){
            //get username from google
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                                .enableAutoManage(this,this)
                                .addApi(Auth.GOOGLE_SIGN_IN_API)
                                .build();

            userName = mFirebaseUser.getDisplayName();
            photoUrl = String.valueOf(mFirebaseUser.getPhotoUrl());

        }else {
            //get username from Constants
            userName = Constants.USERNAME;
        }

        Toast.makeText(GlobalChat.this, "Not null", Toast.LENGTH_SHORT).show();

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Messages, MessageViewHolder>(Messages.class,
                                R.layout.message_list_item,
                                MessageViewHolder.class,
                                mReference.child(Constants.MESSAGES)) {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Messages model, int position) {
                    viewHolder.user.setText(model.getUser());
                    viewHolder.mUser_message.setText(model.getMessage());
//                    viewHolder.mUser.setText(model.getTimestampSentLong());
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
                // to the bottom of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    mRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mFirebaseAdapter);

        mSend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
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
            mFirebaseAuth.signOut();
            if (Constants.FLAG){
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            }
            startActivity(new Intent(GlobalChat.this,Login.class));
            photoUrl = null;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendMessage() {

        String message = mEditText.getText().toString();

        if (!message.trim().equals("")){
            mEditText.setText("");
            Messages messages = new Messages("Anonymous",message);
            mReference.child(userName).push().setValue(messages);
        }else {
            mEditText.setError("Empty message");
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
