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
import com.example.expensemanagement.model.Users;
import com.example.expensemanagement.utils.SecurityUtils;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView tvSignUp;
    UserDb userDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDb = new UserDb(LoginActivity.this);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        checkLoginUser();
    }

    private void checkLoginUser() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    edtUsername.setError("Username can not empty");
                    return;
                }
                String password = edtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    edtPassword.setError("Password can not empty");
                    return;
                }

                if (validateLogin(username, password)) {
                    Users infoUser = userDb.checkLoginUser(username, SecurityUtils.hashPassword(password));
                    if (infoUser != null && infoUser.getUsername() != null) {
                        // login success
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("ID_USER", infoUser.getId());
                        bundle.putString("USER_ACCOUNT", infoUser.getUsername());
                        bundle.putString("USER_EMAIL", infoUser.getEmail());
                        bundle.putInt("ROLE_ID", infoUser.getRoleId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Account Invalid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Account Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        String hashedPassword = SecurityUtils.hashPassword(password);
        // Use hashedPassword for validation against the database.
        return userDb.checkLoginUser(username, hashedPassword) != null;
    }
}
