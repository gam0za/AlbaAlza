package com.example.albaalza.P_AlbaTing.ListGroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.Server.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbaTing_List extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ListData> albaTingAllLists=new ArrayList<>();
    NetworkService networkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alba_ting__list);

        networkService= ApplicationController.getInstance().getNetworkService();

        /*************************알바알자의 전체 알바팅 리스트 불러오기*************************/
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview_allList);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter_AllList(albaTingAllLists,this);
        recyclerView.setAdapter(adapter);
        getList();


//      리사이클러뷰 구분선 추가
        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.recyclerview_line));
        recyclerView.addItemDecoration(dividerItemDecoration);

    }


//    전체 그룹 리스트 불러오는 함수
    public void getList(){
        Log.d("getList()","in");
        Call<AllListResponse> allListResponseCall = networkService.listGroup();
        allListResponseCall.enqueue(new Callback<AllListResponse>() {
            @Override
            public void onResponse(Call<AllListResponse> call, Response<AllListResponse> response) {
                if(response.isSuccessful()){
                    Log.d("onResponse","SUCCESS");
//                    adapter=new Adapter_AllList(response.body().listData,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<AllListResponse> call, Throwable t) {
                Log.d("onFailure","FAIL");
                ApplicationController.getInstance().makeToast("서버 상태를 확인해주세요");
            }
        });
    }
}
