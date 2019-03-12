package com.example.hrapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    private EditText mUserTextEmail;
    private EditText mUserTextPassword;
    private Button mUserLoginButton;
    private TextView mUserTextSignUp;

    private AlertDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        mUserTextEmail = (EditText) findViewById(R.id.user_login_email);
        mUserTextPassword = (EditText) findViewById(R.id.user_login_password);
        mUserLoginButton = (Button) findViewById(R.id.user_login_button);
        mUserTextSignUp = (TextView) findViewById(R.id.user_login_registration);

        mProgressDialog = new ProgressDialog(this);

        mUserLoginButton.setOnClickListener(listener);
        mUserTextSignUp.setOnClickListener(listener);
    }

    private void userLogin() {
        String email = mUserTextEmail.getText().toString();
        String password = mUserTextPassword.getText().toString();
        mProgressDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.LoggingProgressDialog)
                .build();
        mProgressDialog.show();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the credentials =)",
                    Toast.LENGTH_SHORT).show();
        } else {
            mFirebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this,
                                        "Could not login, please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == mUserLoginButton) {
                userLogin();
            }

            if (v == mUserTextSignUp) {
                startActivity(new Intent(v.getContext(), RegistrationActivity.class));
            }
        }
    };
}
