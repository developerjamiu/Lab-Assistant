package com.iita.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
//        Thread welcomeThread = new Thread() {
//
//            @Override
//            public void run() {
//                try {
//                    super.run();
//                    sleep(10000);  //Delay of 10 seconds
//                } catch (Exception e) {
//
//                } finally {
//
//                    Intent i = new Intent(MainActivity.this,
//                            LabOneActivity.class);
//                    startActivity(i);
//                    finish();
//                }
//            }
//        };
//        welcomeThread.start();
    }
}
