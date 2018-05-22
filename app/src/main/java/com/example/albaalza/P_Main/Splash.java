package com.example.albaalza.P_Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.bluehomestudio.progressimage.ProgressPicture;
import com.example.albaalza.P_Login.Login;
import com.example.albaalza.R;

public class Splash extends AppCompatActivity {

    ProgressPicture imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        imageView=(ProgressPicture)findViewById(R.id.imageView);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                imageView.startAnimation();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        }, 3000);// 3 초

    }
}