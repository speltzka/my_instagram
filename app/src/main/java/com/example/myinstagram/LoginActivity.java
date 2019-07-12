package com.example.myinstagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.myinstagram.model.User;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btLogin;
    private Button btSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            // show the signup or login screen
            setContentView(R.layout.activity_login);
            etUsername = findViewById(R.id.etUsername);
            etPassword = findViewById(R.id.etPassword);
            btLogin = findViewById(R.id.btLogin);
            btSignUp = findViewById(R.id.signUp);
            btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String username = etUsername.getText().toString();
                    final String password = etPassword.getText().toString();
                    login(username, password);
                    }
                });

            btSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String username = etUsername.getText().toString();
                    final String password = etPassword.getText().toString();
                    signUp(username, password);
                }
                });

    }

    private void login(String username, String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Login Successful");
                    finish();
                }

                else{
                    Log.e("LoginActivity", "Login Failure");
                    e.printStackTrace();
                }
            }
        });
    }

    private void signUp(final String username, final String password){
        // Create the ParseUser
        User user = new User();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) { if (e == null) {
                //login(username, password);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
