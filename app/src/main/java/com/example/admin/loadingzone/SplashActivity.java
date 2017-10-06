package com.example.admin.loadingzone;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.loadingzone.modules.home.HomeActivity;
import com.example.admin.loadingzone.modules.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            Thread background = new Thread() {
                public void run() {

                    try {
                        // Thread will sleep for 5 seconds
                        sleep(5 * 1000);

                        // After 5 seconds redirect to another intent
                        // After 5 seconds redirect to another intent
                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(i);
                        //Remove activity
                        finish();

                    } catch (Exception e) {

                    }
                }
            };

            // start thread
            background.start();
        }






    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
