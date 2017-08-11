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
        //TextView textView=(TextView)findViewById(R.id.textTitle);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Ho",Toast.LENGTH_LONG).show();
//                Intent i=new Intent(SplashActivity.this,LoginActivity.class);
//                   startActivity(i);
//            }
//        });

// METHOD 1

//        Intent intent = this.getIntent();
//        if (intent != null && intent.getExtras() != null && intent.getExtras().containsKey("name") && (intent.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) == 0) {
//            String name = this.getIntent().getExtras().getString("name");
//            String jobID = this.getIntent().getExtras().getString("job_id");
//
//            if (name.equals("quotation")) {
//                Intent intent1 = new Intent(SplashActivity.this, HomeActivity.class);
//                intent1.putExtra("job_id", jobID);
//                startActivity(intent1);
//            }
//
//            finish();
//        }
//        else {
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
