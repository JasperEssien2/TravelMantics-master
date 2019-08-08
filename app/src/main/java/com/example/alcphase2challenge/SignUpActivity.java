package com.example.alcphase2challenge;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.alcphase2challenge.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;

    private FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        // ...
// Initialize Firebase Auth
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        binding.signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setErrorNull();
registerUser();
            }
        });
        binding.signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, AuthenticationActivity.class));

            }
        });
        binding.getRoot();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void registerUser() {
        final String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString();
         final String email = Objects.requireNonNull(binding.emailEditText.getText()).toString();
        if (validateForm() == 0) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Registration Successful.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, AdminActivity.class);
                                intent.putExtra("email", email);
                                intent.putExtra("password", password);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "Account created already",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }


                    });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private int validateForm() {
        int errorCount = 0;
        String name = Objects.requireNonNull(Objects.requireNonNull(binding.firstAndLastNameEditText.getText()).toString());
        String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString();
        String email = Objects.requireNonNull(binding.emailEditText.getText()).toString();
        if (TextUtils.isEmpty(email)) {
            binding.emailEditText.setError("Required.");
            errorCount++;

        } else {
            binding.emailEditText.setError(null);
        }
        if (TextUtils.isEmpty(password)) {
            binding.passwordEditText.setError("Required.");
            errorCount++;

        } else {
            binding.passwordEditText.setError(null);
        }
        if(TextUtils.isEmpty(name)){
            binding.firstAndLastNameEditText.setError("Required.");
        }else {
            binding.firstAndLastNameEditText.setError(null);
        }

        return errorCount;
    }
    private void setErrorNull() {
        binding.firstAndLastNameEditText.setError(null);
        binding.emailEditText.setError(null);
        binding.passwordEditText.setError(null);


    }
}
