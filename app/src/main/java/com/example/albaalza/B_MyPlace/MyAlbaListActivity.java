package com.example.albaalza.B_MyPlace;

// 3-2 받은 친구신청 확인 => AlbaListPost, AlbaListResponse

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.albaalza.B_MyPlace.Server.AlbaListPost;
import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.Server.NetworkService;

public class MyAlbaListActivity extends AppCompatActivity {

    NetworkService networkService;
    AlbaListPost albaListPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_alba_list);

        networkService= ApplicationController.getInstance().getNetworkService();



    }
}
