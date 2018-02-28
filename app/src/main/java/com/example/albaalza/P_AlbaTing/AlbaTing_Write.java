package com.example.albaalza.P_AlbaTing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.Server.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbaTing_Write extends AppCompatActivity {

    private EditText ting_title, ting_contents;
    private Button write_button;
    private NetworkService networkService;
    private WriteTingPost writeTingPost;
    private String type;
    private String writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alba_ting__write);

        networkService= ApplicationController.getInstance().getNetworkService();

        ting_title=(EditText)findViewById(R.id.ting_title);
        ting_contents=(EditText)findViewById(R.id.ting_contents);
        write_button=(Button)findViewById(R.id.write_button);
        type="공지";


        write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writePost();
            }
        });




    }

    public void writePost(){
        Log.d("func","함수 들어옴!");
        writer="abaz";
        writeTingPost=new WriteTingPost(ting_title.getText().toString(),ting_contents.getText().toString(), writer);
        retrofit2.Call<WriteTingResponse> writeTingResponseCall=networkService.postTing(writeTingPost);
        writeTingResponseCall.enqueue(new Callback<WriteTingResponse>() {
            @Override
            public void onResponse(retrofit2.Call<WriteTingResponse> call, Response<WriteTingResponse> response) {
                if(response.isSuccessful()){
                    Log.d("tag","SUCCESS");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<WriteTingResponse> call, Throwable t) {

            }
        });

    }
}
