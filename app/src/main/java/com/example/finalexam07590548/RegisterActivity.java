package com.example.finalexam07590548;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalexam07590548.R;
import com.example.finalexam07590548.User;

public class RegisterActivity extends AppCompatActivity {
    private EditText mFullname;
    private EditText mUsername;
    private EditText mPassword;
    private Button mRegister;

    private Context mContext;
    private UserManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mManager = new UserManager(this);
        mContext = this;

        mFullname = (EditText) findViewById(R.id.full_name_edit_text);
        mUsername = (EditText) findViewById(R.id.username_edit_text);
        mPassword = (EditText) findViewById(R.id.password_edit_text);
        mRegister = (Button) findViewById(R.id.register_button);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname = mFullname.getText().toString().trim().toLowerCase();
                String username = mUsername.getText().toString().trim().toLowerCase();
                String password = mPassword.getText().toString();

                if (password.equals(fullname)) {
                    User user = new User(username, password);
                    long rowId = mManager.registerUser(user);

                    if (rowId == -1) {
                        String message = getString(R.string.register_error_message);
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    } else {
                        String message = getString(R.string.register_success);
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } else {
                    String message = getString(R.string.register_password_error);
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
