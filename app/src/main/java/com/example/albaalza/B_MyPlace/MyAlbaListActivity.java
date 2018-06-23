package com.example.albaalza.B_MyPlace;

// 3-2 받은 친구신청 확인 => AlbaListPost, AlbaListResponse

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.albaalza.B_MyPlace.Server.AlbaListData;
import com.example.albaalza.B_MyPlace.Server.AlbaListPost;
import com.example.albaalza.B_MyPlace.Server.AlbaListResponse;
import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.Server.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAlbaListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter_AlbaList adapter;
    private RecyclerView.LayoutManager layoutManager;
    private NetworkService networkService;
    private AlbaListPost albaListPost;

    private ArrayList<AlbaListData> albalistItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_alba_list);
        networkService= ApplicationController.getInstance().getNetworkService();

        recyclerView=(RecyclerView)findViewById(R.id.recycler_albalist);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mylistrequest("MINb");

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getApplicationContext(),
                LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.recyclerview_line));
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    public void mylistrequest(String id){
        albaListPost=new AlbaListPost(id);
        Call<AlbaListResponse> albaListResponseCall=networkService.mylistrequest(albaListPost);

        albaListResponseCall.enqueue(new Callback<AlbaListResponse>() {
            @Override
            public void onResponse(Call<AlbaListResponse> call, Response<AlbaListResponse> response) {
                if(response.isSuccessful()){
                    albalistItems=response.body().albaListData;
                    adapter=new Adapter_AlbaList(albalistItems,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<AlbaListResponse> call, Throwable t) {
                Log.d("FAIL","FAIL");
                ApplicationController.getInstance().makeToast("서버 연결상태를 확인해주세요:P");
            }
        });
    }
}
