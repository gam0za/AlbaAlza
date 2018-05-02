package com.example.albaalza.P_AlbaTing.MyListGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.albaalza.P_AlbaTing.CreateGroup.AlbaTing_Add;
import com.example.albaalza.P_AlbaTing.ListGroup.AlbaTing_List;
import com.example.albaalza.P_AlbaTing.ListPost.AlbaTing_Detail;
import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.Server.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AlbaTing extends Fragment implements View.OnClickListener{
    private ImageView Search_AlbaTing;
    private com.melnykov.fab.FloatingActionButton Add_AlbaTing;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MyGroupData> albaTingLists = new ArrayList<>();
    private NetworkService networkService;
    private MyGroupListPost myGroupListPost;
    public String gname;

    public AlbaTing() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alba_ting, container, false);
        networkService= ApplicationController.getInstance().getNetworkService();
        gname="TESTGROUP2";

        /*************************알바팅 새로 만들기/ 알바팅 검색 버튼*************************/
        Add_AlbaTing = (com.melnykov.fab.FloatingActionButton)view.findViewById(R.id.Add_Albating);
        Search_AlbaTing = (ImageView)view.findViewById(R.id.Search_Albating);

//        알바팅 새로만들기
        Add_AlbaTing.setOnClickListener(this);
//        알바팅 검색하기
        Search_AlbaTing.setOnClickListener(this);


        /*************************알바팅 리스트 불러오기*************************/
        recyclerView = view.findViewById(R.id.recyclerview_albatinglist);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter_AlbaTingList(albaTingLists, clickEvent,getActivity());
        recyclerView.setAdapter(adapter);
        Add_AlbaTing.attachToRecyclerView(recyclerView);

//         mylistgroup();

//       리사이클러뷰 구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.recyclerview_line));
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Add_Albating:
                Intent i1=new Intent(getContext(),AlbaTing_Add.class); //알바팅 새로 생성
                startActivity(i1);
                break;
            case R.id.Search_Albating:
                Intent i2=new Intent(getContext(),AlbaTing_List.class); //알바팅 전체 리스트로 이동
                startActivity(i2);
                break;
        }
    }

    public void mylistgroup(){
        Log.d("function","mylistgroup");
        myGroupListPost=new MyGroupListPost("abaz");
        Call<MyGroupListResponse> myGroupListResponseCall=networkService.myGroup(myGroupListPost);
        myGroupListResponseCall.enqueue(new Callback<MyGroupListResponse>() {
            @Override
            public void onResponse(Call<MyGroupListResponse> call, Response<MyGroupListResponse> response) {
                Log.d("result","onResponse");
                if(response.isSuccessful()){
//                    gname=response.body().myGroupData
                    adapter = new Adapter_AlbaTingList(albaTingLists, clickEvent,getActivity());
                    recyclerView.setAdapter(adapter);
                    Add_AlbaTing.attachToRecyclerView(recyclerView);
                    ApplicationController.getInstance().makeToast("성공"+gname);
                    Log.d("result","SUCCESS");
                }
            }

            @Override
            public void onFailure(Call<MyGroupListResponse> call, Throwable t) {
                    ApplicationController.getInstance().makeToast("실패"+gname);
                    Log.d("result","FAIL");
            }

        });
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(),AlbaTing_Detail.class);
            String albating_name=gname;
            Log.d("넘겨주는 값",albating_name);
            intent.putExtra("albating_name",albating_name);
            startActivity(intent);
        }
    };
}