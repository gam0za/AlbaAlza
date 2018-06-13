package com.example.albaalza.P_Main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.albaalza.P_Chat.ChatActivity;
import com.example.albaalza.P_Chat.ChattingRoomActivity;
import com.example.albaalza.P_Chat.MessageActivity;
import com.example.albaalza.P_Login.Login;
import com.example.albaalza.R;
import com.nipunbirla.boxloader.BoxLoaderView;

public class LoadActivity extends AppCompatActivity {
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Handler handler = new Handler();
        i=getIntent();
        if(i.getStringExtra("type").equals("home")){
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    BoxLoaderView boxLoader = (BoxLoaderView)findViewById(R.id.progress);
                    boxLoader.setStrokeColor(R.color.alba_maincolor);
                    boxLoader.setLoaderColor(Color.BLUE);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);// 3 초
        }else if(i.getStringExtra("type").equals("MessageActivity")){
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    BoxLoaderView boxLoader = (BoxLoaderView)findViewById(R.id.progress);
                    boxLoader.setStrokeColor(R.color.alba_maincolor);
                    boxLoader.setLoaderColor(Color.BLUE);
                    Intent intent = new Intent(getApplicationContext(),MessageActivity.class);
                    intent.putExtra("chat_room_name",i.getStringExtra("chat_room_name"));
                    intent.putExtra("chat_user_name",i.getStringExtra("chat_user_name"));
                    startActivity(intent);
                    finish();
                }
            }, 3000);// 3 초
        }





    }
}
