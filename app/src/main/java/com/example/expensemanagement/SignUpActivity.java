package com.example.expensemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanagement.database.UserDb;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    EditText etUsername, etEmail, etPassword, etConfirmPassword;
    Button btnSignUp;
    TextView tvLogIn;
    UserDb userDb;

    // Regex patterns
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{6,}$"); // 6+ alphanumeric chars
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{6,}$"); // 6+ any chars
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$" // Basic email format
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userDb = new UserDb(SignUpActivity.this);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLogIn = findViewById(R.id.tvLogIn);

        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signupAccount();
    }

    private void signupAccount() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                // Check for empty fields
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(email) ||
                        TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate username (6+ alphanumeric characters)
                if (!USERNAME_PATTERN.matcher(user).matches()) {
                    Toast.makeText(SignUpActivity.this,
                            "Username must be at least 6 alphanumeric characters!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate email
                if (!EMAIL_PATTERN.matcher(email).matches()) {
                    Toast.makeText(SignUpActivity.this,
                            "Please enter a valid email address!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate password (6+ characters)
                if (!PASSWORD_PATTERN.matcher(password).matches()) {
                    Toast.makeText(SignUpActivity.this,
                            "Password must be at least 6 characters!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate confirm password matches password
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this,
                            "Passwords do not match!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if username or email already exists
                boolean check = userDb.checkExistsUsernameAndEmail(user, email);
                if (check) {
                    Toast.makeText(SignUpActivity.this,
                            "Username or email already exists!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Attempt to create account
                long insert = userDb.insertUserAccount(user, password, email);
                if (insert == -1) {
                    Toast.makeText(SignUpActivity.this,
                            "Sign up failed!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpActivity.this,
                            "Account created successfully!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Close SignUpActivity
                }
            }
        });
    }
}