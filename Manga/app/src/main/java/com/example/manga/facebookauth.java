package com.example.manga;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;

import java.util.Arrays;

public class facebookauth extends MainActivity {

    FirebaseAuth mAuth;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

        mAuth= FirebaseAuth.getInstance();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG,"OnSuccess" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {

        Log.d(TAG,"handleFacebookAccessToken" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(facebookauth.this, "Facebook Login Successful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent i = new Intent(facebookauth.this,HomeActivity.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(facebookauth.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user){

    }
}