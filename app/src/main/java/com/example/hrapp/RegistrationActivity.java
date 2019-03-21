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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    private EditText mEmailText;
    private EditText mPasswordText;
    private Button mRegisterButton;

    private AlertDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mEmailText = (EditText) findViewById(R.id.user_reg_email);
        mPasswordText = (EditText) findViewById(R.id.user_reg_password);
        mRegisterButton = (Button) findViewById(R.id.user_reg_button);

        mRegisterButton.setOnClickListener(registerListener);
    }

    private void registerUser() {
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();
        mProgressDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.RegisteringProgressDialog)
                .build();
        mProgressDialog.show();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the credentials =)",
                    Toast.LENGTH_SHORT).show();
        }

        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this,
                                    "Registered Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            Toast.makeText(RegistrationActivity.this,
                                    "Could not register, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            registerUser();
        }
    };
}
