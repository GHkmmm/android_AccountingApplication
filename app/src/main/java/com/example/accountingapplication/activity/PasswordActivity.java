package com.example.accountingapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.accountingapplication.R;
import com.example.accountingapplication.utils.MyApplication;

public class PasswordActivity extends AppCompatActivity {
    private EditText passwordVal;
    private MyApplication myApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        final Intent intent = getIntent();
        myApplication = (MyApplication) getApplication();
        passwordVal = findViewById(R.id.password);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordVal.getText().toString();
                if (password != null && password.equals(intent.getStringExtra("password"))){
                    Toast.makeText(PasswordActivity.this, "密码正确！", Toast.LENGTH_SHORT).show();
                    myApplication.isLocked = 0;
                    PasswordActivity.this.finish();
                }else {
                    Toast.makeText(PasswordActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                    passwordVal.setText("");
                }
            }
        });
    }
}
