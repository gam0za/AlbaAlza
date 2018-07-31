package com.example.albaalza.UI.B_MyPlace;

// 3-2 받은 친구신청 확인 => AlbaListPost, AlbaListResponse

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albaalza.UI.B_MyPlace.Server.AcceptPost;
import com.example.albaalza.UI.B_MyPlace.Server.AcceptResponse;
import com.example.albaalza.UI.B_MyPlace.Server.AlbaListPost;
import com.example.albaalza.UI.B_MyPlace.Server.AlbaListResponse;
import com.example.albaalza.R;
import com.example.albaalza.Network.ApplicationController;
import com.example.albaalza.Network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAlbaListActivity extends AppCompatActivity {


    private NetworkService networkService;
    private AlbaListPost albaListPost;
    private AcceptPost acceptPost;
    private TextView albalist_name,albalist_date;
    private ImageView accept;
    private Boolean status;
    private String rid,id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_alba_list);
        networkService= ApplicationController.getInstance().getNetworkService();
        SharedPreferences sharedPreferences;
        albalist_name=(TextView)findViewById(R.id.albalist_name);
        albalist_date=(TextView)findViewById(R.id.albalist_date);
        accept=(ImageView)findViewById(R.id.accept);
        sharedPreferences=getSharedPreferences("account", Context.MODE_PRIVATE);
        id=sharedPreferences.getString("id","MINb");

        mylistrequest(id);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptrequest(rid);
            }
        });



    }
//    친구 신청 확인
    public void mylistrequest(String id){
        Log.d("ID",id);
        albaListPost=new AlbaListPost(id);
        Call<AlbaListResponse> albaListResponseCall=networkService.mylistrequest(albaListPost);

        albaListResponseCall.enqueue(new Callback<AlbaListResponse>() {
            @Override
            public void onResponse(Call<AlbaListResponse> call, Response<AlbaListResponse> response) {
                if(response.isSuccessful()){
                    ApplicationController.getInstance().makeToast(response.body().worker);
                    albalist_name.setText(response.body().worker);
                    albalist_date.setText(response.body().updated_at);
                    status=response.body().status;
                    rid=response.body().objectid;

                    if(status==true){
                        accept.setImageResource(R.drawable.accepted);
                    }

                }
            }

            @Override
            public void onFailure(Call<AlbaListResponse> call, Throwable t) {
                Log.d("FAIL","FAIL");
                ApplicationController.getInstance().makeToast("서버 연결상태를 확인해주세요:P");
            }
        });
    }
//    친구 신청 확인 보내기
    public void acceptrequest(String rid){
        Log.d("친구신청 확인","function in");
        acceptPost=new AcceptPost(rid);
        Call<AcceptResponse> acceptResponseCall=networkService.acceptrequest(acceptPost);

        acceptResponseCall.enqueue(new Callback<AcceptResponse>() {
            @Override
            public void onResponse(Call<AcceptResponse> call, Response<AcceptResponse> response) {
                if(response.isSuccessful()){
                    ApplicationController.getInstance().makeToast("친구 요청을 수락했습니다.");
                    accept.setImageResource(R.drawable.accepted);
                }
            }

            @Override
            public void onFailure(Call<AcceptResponse> call, Throwable t) {

            }
        });
    }
}
