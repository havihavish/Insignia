package com.example.android.insignia2k16.chat.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.insignia2k16.Constants;
import com.example.android.insignia2k16.R;
import com.example.android.insignia2k16.chat.model.Users;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.security.SecureRandom;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText mUsernameInput,mEmailInput,mPassworInput;
    String mUsername,mEmail,mPassword;
    Button mSignUpBtn;
    Firebase mRef;
    TextView mSignInText;
    private SecureRandom mRandom = new SecureRandom();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUsernameInput = (EditText)findViewById(R.id.sign_up_username);
        mEmailInput = (EditText)findViewById(R.id.sign_up_email);
        mPassworInput = (EditText)findViewById(R.id.sign_up_password);
        mSignUpBtn = (Button)findViewById(R.id.sign_up_button);
        mSignInText = (TextView) findViewById(R.id.Sign_in);

        mRef = new Firebase(Constants.FIREBASE_BASE_URL);

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        mSignInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createUser() {

        final Dialog dialog = new Dialog(SignUp.this);

        dialog.setContentView(R.layout.authentication_dialog);
        dialog.setCancelable(false);
        dialog.show();

        mUsername = mUsernameInput.getText().toString();
        mEmail = mEmailInput.getText().toString();
//        String mPassword = new BigInteger(130, mRandom).toString(32);
        mPassword = mPassworInput.getText().toString();

        boolean validEmail = isEmailValid(mEmail);

        //create a new user
        mRef.createUser(mEmail, mPassword, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                dialog.dismiss();
                Users user = new Users(mUsername,mEmail);

                mRef.child(Constants.USERS).push().setValue(user);
                Intent intent = new Intent(SignUp.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                dialog.dismiss();
                Toast.makeText(SignUp.this, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isEmailValid(String email) {

        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEmailInput.setError("Invalid Email");
            return false;
        }
        return isGoodEmail;

    }

}
