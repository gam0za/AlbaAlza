package com.example.albaalza.P_SignUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albaalza.P_Login.Login;
import com.example.albaalza.R;

import static com.example.albaalza.R.drawable.boss;

public class SignUp extends AppCompatActivity {
   private ImageView signup;
   private ImageView actor;
   private TextView actor_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Intent getStr=getIntent();
        String str = getStr.getStringExtra("actor");

        actor=(ImageView)findViewById(R.id.actor);
        actor_text=(TextView)findViewById(R.id.actor_t);

        if(str.equals("boss")){
            actor_text.setText("관리자 계정입니다");
        }else if(str.equals("worker")){
            actor_text.setText("알바생 계정입니다");
        }

        signup=(ImageView)findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }
}
