package com.example.albaalza.P_AlbaTing.AddPost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.albaalza.P_AlbaTing.ListPost.AlbaTing_Detail;
import com.example.albaalza.P_Login.UserData;
import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.Server.NetworkService;

import retrofit2.Callback;
import retrofit2.Response;

public class AlbaTing_Write extends AppCompatActivity {

    private EditText ting_title, ting_contents;
    private Button write_button;
    private NetworkService networkService;
    private WriteTingPost writeTingPost;
    private UserData userData;
    private String gname;
    private String writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alba_ting__write);

        networkService= ApplicationController.getInstance().getNetworkService();

        ting_title=(EditText)findViewById(R.id.ting_title);
        ting_contents=(EditText)findViewById(R.id.ting_contents);
        write_button=(Button)findViewById(R.id.write_button);
        userData=new UserData();

        Intent get = getIntent();
        gname=get.getStringExtra("gname");
        writer = "abaz";

        write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("click","click");
                writePost();
            }
        });




    }

    public void writePost(){
        Log.d("func","함수 들어옴!");
        writeTingPost=new WriteTingPost(ting_title.getText().toString(),ting_contents.getText().toString(),"abaz","TESTGROUP3");
        retrofit2.Call<WriteTingResponse> writeTingResponseCall=networkService.postTing(writeTingPost);
        writeTingResponseCall.enqueue(new Callback<WriteTingResponse>() {
            @Override
            public void onResponse(retrofit2.Call<WriteTingResponse> call, Response<WriteTingResponse> response) {
                if(response.isSuccessful()){
                    Log.d("tag","SUCCESS");
                    ApplicationController.getInstance().makeToast("글이 등록되었습니다 ");
                    Intent intent = new Intent(AlbaTing_Write.this,AlbaTing_Detail.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<WriteTingResponse> call, Throwable t) {
                Log.d("tag","FAIL");
                ApplicationController.getInstance().makeToast(writer+", "+gname);
            }
        });

    }
}
