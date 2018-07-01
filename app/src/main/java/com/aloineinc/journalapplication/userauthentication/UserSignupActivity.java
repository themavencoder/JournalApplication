/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aloineinc.journalapplication.userauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aloineinc.journalapplication.MainActivity;
import com.aloineinc.journalapplication.R;
import com.aloineinc.journalapplication.localdb.utilities.EmailValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserSignupActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText mInputEmail, mInputPassword;
    private ProgressBar mProgressBar;
    private FirebaseAuth mFirebaseAuth;
    private CoordinatorLayout coordinatorLayout;
    private UserContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        init();
        presenter = new UserPresenter();
    }


    private void init() {
        mFirebaseAuth = FirebaseAuth.getInstance();

        mInputEmail = findViewById(R.id.email);
        mInputPassword = findViewById(R.id.password);
        mProgressBar = findViewById(R.id.progressBar);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_up_button).setOnClickListener(this);
        findViewById(R.id.btn_reset_password).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reset_password:
                startActivity(new Intent(UserSignupActivity.this, UserResetPasswordActivity.class));
                break;
            case R.id.sign_in_button:
                finish();
                break;
            case R.id.sign_up_button:
                doSignUpbtn();
                break;


        }


    }

    private void doSignUpbtn() {
        presenter.saveLoginDetails(mInputEmail.getText().toString(),mInputPassword.getText().toString());
        if (checkEnteredValue(presenter.showEmail(), presenter.showPassword())) return;

        mProgressBar.setVisibility(View.VISIBLE);

        firebaseAuth(presenter.showEmail(), presenter.showPassword());

    }

    private void firebaseAuth(String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(UserSignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(UserSignupActivity.this, "Successful:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(UserSignupActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(UserSignupActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    private boolean checkEnteredValue(String email, String password) {
        Snackbar snackbar;
        if (TextUtils.isEmpty(email)) {
            mInputEmail.setError("Email field is empty");
            snackbar = Snackbar
                    .make(coordinatorLayout, "Enter email address", Snackbar.LENGTH_LONG);
            snackbar.show(); Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (!EmailValidator.isValidEmail(email)) {
            mInputEmail.setError("Enter a valid email address");
            return true;
        }

        if (TextUtils.isEmpty(password)) {
            mInputPassword.setError("Password field is empty");
            snackbar = Snackbar
                    .make(coordinatorLayout, "Enter password", Snackbar.LENGTH_LONG);
            snackbar.show();
            return true;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgressBar.setVisibility(View.GONE);
    }


}
