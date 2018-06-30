package com.aloineinc.journalapplication.userauthentication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aloineinc.journalapplication.R;
import com.aloineinc.journalapplication.localdb.utilities.EmailValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class UserResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText mInputEmail;
    private FirebaseAuth mFirebaseAuth;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reset_password);
        init();

    }

    private void init() {
        mFirebaseAuth = FirebaseAuth.getInstance();

        mInputEmail = findViewById(R.id.email);
        mProgressBar = findViewById(R.id.progressBar);
        findViewById(R.id.btn_reset_password).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_reset_password:
               doRestBtn();
                break;


        }

    }

    private void doRestBtn() {
        String email = mInputEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            mInputEmail.setError("Enter registered mail");
            Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!EmailValidator.isValidEmail(email)) {
            mInputEmail.setError("Enter a valid email address");
            return;
        }
        mProgressBar.setVisibility(View.VISIBLE);
        mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UserResetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                }

                mProgressBar.setVisibility(View.GONE);

            }
        });
    }
}
