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
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aloineinc.journalapplication.MainActivity;
import com.aloineinc.journalapplication.R;
import com.aloineinc.journalapplication.localdb.utilities.EmailValidator;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class UserLoginActivity extends AppCompatActivity implements  View.OnClickListener {
    private TextInputEditText mInputEmail, mInputPassword;
    private ProgressBar mProgressBar;
    private FirebaseAuth mFirebaseAuth;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private final String TAG = "USERLOGIN_ACTIVITY";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private CoordinatorLayout coordinatorLayout;
    private UserContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        init();
        presenter = new UserPresenter();
        authListener();
        googleSignIn();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mProgressBar.setVisibility(View.VISIBLE);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void init() {
        mInputEmail = findViewById(R.id.email);
        mInputPassword = findViewById(R.id.password);
        mProgressBar = findViewById(R.id.progressBar);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        findViewById(R.id.btn_signup).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_reset_password).setOnClickListener(this);
        findViewById(R.id.googleButton).setOnClickListener(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    private void googleSignIn() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient =
                new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(UserLoginActivity.this, "Error occured", Toast.LENGTH_SHORT).show();

            }
        })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void authListener() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    startActivity(new Intent(UserLoginActivity.this, MainActivity.class));
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                startActivity(new Intent(UserLoginActivity.this, UserSignupActivity.class));

                break;
            case R.id.btn_reset_password:
                startActivity(new Intent(UserLoginActivity.this, UserResetPasswordActivity.class));

                break;
            case R.id.btn_login:
                doLoginBtn();

                break;
            case R.id.googleButton:
                mProgressBar.setVisibility(View.GONE);
                signIn();

        }

    }

    private void doLoginBtn() {
        presenter.saveLoginDetails(mInputEmail.getText().toString(),mInputPassword.getText().toString());
        if (checkLoginDetails(presenter.showEmail(), presenter.showPassword())) return;


        mProgressBar.setVisibility(View.VISIBLE);

        mFirebaseAuth.signInWithEmailAndPassword(presenter.showEmail(), presenter.showPassword()).addOnCompleteListener(UserLoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mProgressBar.setVisibility(View.GONE);
                if (!task.isSuccessful()) {
                    if (presenter.showPassword().length() < 5) {
                        mInputPassword.setError(getString(R.string.minimum_password));



                    } else {
                        Toast.makeText(UserLoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                    }
                } else {
                    startActivity(new Intent(UserLoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        });

    }

    private boolean checkLoginDetails(String email, String password) {
        Snackbar snackbar;
        if (TextUtils.isEmpty(email)) {
            mInputEmail.setError("Email field is empty");
            snackbar = Snackbar
                    .make(coordinatorLayout, "Enter email address", Snackbar.LENGTH_LONG);
            snackbar.show();
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
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                assert account != null;
                firebaseAuthWithGoogle(account);
            }

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mProgressBar.setVisibility(View.GONE);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");


                        } else {
                            // If sign in fails, display a message to the user.
                            mProgressBar.setVisibility(View.GONE);
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(UserLoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


}