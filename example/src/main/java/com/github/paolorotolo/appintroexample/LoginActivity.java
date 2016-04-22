package com.github.paolorotolo.appintroexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by hangshi on 2016-04-21.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.login_activity);
    }

    public void showSignupActiviy(View v) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
