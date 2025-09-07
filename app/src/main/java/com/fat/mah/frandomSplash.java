package com.fat.mah;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fat.mah.main.main.MainActivity;

public class frandomSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_frandom_splash);


        new Handler ().postDelayed (new Runnable () {

            @Override
            public void run() {
                Intent splashIntent=new Intent(frandomSplash.this, MainActivity.class);
                startActivity(splashIntent);
                finish();
            }
        },3000);
    }
}
