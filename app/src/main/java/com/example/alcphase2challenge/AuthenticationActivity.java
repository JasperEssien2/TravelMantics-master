package com.example.alcphase2challenge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.alcphase2challenge.databinding.ActivityAuthenticationBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Collections;
import java.util.Objects;


public class AuthenticationActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    ActivityAuthenticationBinding binding;
    FirebaseAuth firebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInClient googleSignInClient;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(AuthenticationActivity.this, gso);

        binding.emailSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailSignIn();
            }
        });
        binding.googleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });
        binding.signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthenticationActivity.this, SignUpActivity.class));
            }
        });
    }

    private void emailSignIn() {
        AuthUI.IdpConfig idpConfig = new AuthUI.IdpConfig.EmailBuilder().build();

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(idpConfig))
                        .build(),
                RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final ProgressDialog progressDialog = new ProgressDialog(AuthenticationActivity.this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        if (requestCode == RC_SIGN_IN) {
            progressDialog.dismiss();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = new Intent(AuthenticationActivity.this, AdminActivity.class);
            startActivity(intent);
        }
            if (resultCode == Activity.RESULT_OK) {
                progressDialog.dismiss();
                // Successfully signed in
                switch (requestCode) {
                    case 101:
                        try {
                            // The Task returned from this call is always completed, no need to attach
                            // a listener.
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            onLoggedIn(account);
                        } catch (ApiException e) {
                            // The ApiException status code indicates the detailed failure reason.
                            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                        }
                        break;
                }

                // ...
            }
    }

    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra(AdminActivity.GOOGLE_ACCOUNT, googleSignInAccount);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (alreadyloggedAccount != null) {
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
            onLoggedIn(alreadyloggedAccount);
        } else {
            Log.d(TAG, "Not logged in");
        }
    }
//
//                else{
//                    // Sign in failed. If response is null the user canceled the
//                    // sign-in flow using the back button. Otherwise check
//                    // response.getError().getErrorCode() and handle the error.
//                    // ...
//                }
//            }
        }
