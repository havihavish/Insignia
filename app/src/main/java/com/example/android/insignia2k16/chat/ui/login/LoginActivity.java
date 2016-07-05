package com.example.android.insignia2k16.chat.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.insignia2k16.Constants;
import com.example.android.insignia2k16.R;
import com.example.android.insignia2k16.chat.ui.ChatActivity;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {

    EditText mEmailField,mPasswordField;
    String mEmail,mPassword;
    Button mLoginButton;
    TextView mSignUp;
    Firebase mRef;
    ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialise ui elements
        mEmailField = (EditText)findViewById(R.id.login_email);
        mPasswordField = (EditText)findViewById(R.id.login_password);
        mLoginButton = (Button)findViewById(R.id.login_button);
        mSignUp = (TextView)findViewById(R.id.sign_up_text);

        mRef = new Firebase(Constants.FIREBASE_BASE_URL).child(Constants.USERS);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUser();
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUp.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void validateUser() {

        mEmail = mEmailField.getText().toString();
        mPassword = mPasswordField.getText().toString();

        final Dialog dialog = new Dialog(LoginActivity.this);

        dialog.setContentView(R.layout.authentication_dialog);
        dialog.setTitle("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        mRef.authWithPassword(mEmail, mPassword, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {

                dialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Toast.makeText(LoginActivity.this, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }


}
