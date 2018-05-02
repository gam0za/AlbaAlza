package com.example.albaalza.P_AlbaTing.CreateGroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.albaalza.P_Login.UserData;
import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.Server.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbaTing_Add extends AppCompatActivity {

    EditText addting_name,addting_description;
    ImageView addting_open,addting_close,addting_button;
    private NetworkService networkService;
    private CreateGroupPost createGroupPost;
    String gtype,admin; //그룹 이름, 그룹 공개여부, 사용자 이름
//    SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
    UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alba_ting_add);

        networkService=ApplicationController.getInstance().getNetworkService();
        addting_name=(EditText) findViewById(R.id.addting_name);//알바팅 이름
        addting_description=(EditText) findViewById(R.id.addting_description);//알바팅 소개
        addting_open=(ImageView)findViewById(R.id.addting_open);
        addting_close=(ImageView)findViewById(R.id.addting_close);
        addting_button=(ImageView)findViewById(R.id.addting_button);
        userData=new UserData();

        gtype="public";
        admin="abaz";

//        공개
        addting_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gtype="public";
                Toast.makeText(AlbaTing_Add.this,gtype,Toast.LENGTH_SHORT).show();
            }
        });
//        비공개
        addting_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gtype="private";
                Toast.makeText(AlbaTing_Add.this,gtype,Toast.LENGTH_SHORT).show();
            }
        });

//        확인 버튼
        addting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGroup();

            }
        });

    }

    public void createGroup(){
        createGroupPost=new CreateGroupPost(addting_name.getText().toString(),gtype,admin);
        Call<CreateGroupResponse> createGroupResponseCall=networkService.createGroup(createGroupPost);
        createGroupResponseCall.enqueue(new Callback<CreateGroupResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CreateGroupResponse> call, Response<CreateGroupResponse> response) {
                if(response.isSuccessful()){
                    Log.d("createGroup","SUCCESS");
                    ApplicationController.getInstance().makeToast("그룹이 생성되었습니다");
                    finish();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CreateGroupResponse> call, Throwable t) {
                Log.d("createGroup","FAIL"+addting_name.getText().toString());
                ApplicationController.getInstance().makeToast("서버 상태를 확인해 주세요.");
            }
        });
    }
}
