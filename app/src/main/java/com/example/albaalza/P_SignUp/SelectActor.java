package com.example.albaalza.P_SignUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.albaalza.R;

public class SelectActor extends AppCompatActivity {
    private ImageView worker,boss;
    public int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_actor);
        worker= (ImageView)findViewById(R.id.worker);
        boss=(ImageView)findViewById(R.id.boss);

        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                intent.putExtra("actor","worker");
                startActivity(intent);
            }
        });
        boss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=2;
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                intent.putExtra("actor","boss");
                startActivity(intent);
            }
        });
    }
}
