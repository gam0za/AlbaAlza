package com.example.albaalza.P_AlbaTing.MyListGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
//    @Override
//    public void onResume() {
//        super.onResume();
//        adapter_albaTingList.notifyDataSetChanged();
//    }

    private ImageView Search_AlbaTing;
    private ImageView Add_AlbaTing;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter_albaTingList= new Adapter_AlbaTingList(myGroupListResponseArrayList,getActivity());

        Log.d("CREATE","CREATE");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("CREATED","CREATED");
    }

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private NetworkService networkService;
    private Adapter_AlbaTingList adapter_albaTingList;
    private ArrayList<MyGroupData> myGroupListResponseArrayList;
    MyGroupListPost myGroupListPost;

    public String gname;

    public AlbaTing() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        onActivityCreated(savedInstanceState);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alba_ting, container, false);

        Log.d("CREATEVIEW","CREATEVIEW");

        /*************************알바팅 새로 만들기/ 알바팅 검색 버튼*************************/
        Add_AlbaTing = (ImageView) view.findViewById(R.id.Add_Albating);
        Search_AlbaTing = (ImageView)view.findViewById(R.id.Search_Albating);

//        알바팅 새로만들기
        Add_AlbaTing.setOnClickListener(this);
//        알바팅 검색하기
        Search_AlbaTing.setOnClickListener(this);

        networkService=ApplicationController.getInstance().getNetworkService();


        /*************************알바팅 리스트 불러오기*************************/

        recyclerView = view.findViewById(R.id.recyclerview_albatinglist);
        layoutManager = new LinearLayoutManager(getActivity()); //layouManager 선언
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);//recyclerview layoutManager 설정
        //       리사이클러뷰 구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.recyclerview_line));
        recyclerView.addItemDecoration(dividerItemDecoration);

//        adapter_albaTingList= new Adapter_AlbaTingList(myGroupListResponseArrayList,getActivity());


//        adapter_albaTingList.setItem(myGroupListResponseArrayList);
        getList();




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

    public void getList(){
        Log.d("in","func");
       myGroupListPost=new MyGroupListPost("doby");
        Call<MyGroupListResponse> myGroupListResponseCall=networkService.myGroup(myGroupListPost);
        myGroupListResponseCall.enqueue(new Callback<MyGroupListResponse>() {
            @Override
            public void onResponse(Call<MyGroupListResponse> call, Response<MyGroupListResponse> response) {
                if(response.isSuccessful()){
                    Log.d("getList","SUCCESS");
                    myGroupListResponseArrayList=response.body().myGroupData;
                    adapter_albaTingList= new Adapter_AlbaTingList(myGroupListResponseArrayList,getActivity());
                    recyclerView.setAdapter(adapter_albaTingList);//recyclerview adapter 설정


                }
            }

            @Override
            public void onFailure(Call<MyGroupListResponse> call, Throwable t) {

            }
        });
    }


}