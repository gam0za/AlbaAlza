package com.example.albaalza.P_AlbaTing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albaalza.R;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AlbaTing2Fragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton floating2;

    public AlbaTing2Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view=(ViewGroup) inflater.inflate(R.layout.fragment_alba_ting2, container, false);
        recyclerView = view.findViewById(R.id.recyclerview2);
        floating2=view.findViewById(R.id.floating2);
        floating2.attachToRecyclerView(recyclerView);

        floating2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(),AlbaTing_write.class);
                i.putExtra("albating","공지");
                startActivity(i);
            }
        });

        Context mContext=getActivity().getApplicationContext();

        List<AlbaTingData> albaTingDataList=new ArrayList<>();

        //리사이클러뷰에 뷰가 추가되는 순간
        //서버에서 가져온걸 붙이던가, 아니면 AlbaTingData 클래스에 서버에서 가져온 데이터를 추가해야 할 것 같다.

//        int profile, String name, String time, int image, String title, int comment_image, String comment
        albaTingDataList.add(new AlbaTingData(R.drawable.boss,"사장님","2017.09.13.07:38",R.drawable.coffeemachine,
                "커피머신 입구 청소해주세요~",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.worker,"알바생","2017.09.5.11:11",R.drawable.cakeandcoffee,
                "신제품 출시되었어용!!",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.worker,"알바생","2017.09.5.11:11",R.drawable.insurance,
                "4대보험 가입 여부 확인방법",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.example1,"알바생","2017.09.5.11:11",R.drawable.coldbrew,
                "콜드브루",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.example2,"알바생","2017.09.5.11:11",R.drawable.coffee2,
                "커피 이미지입니다~",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.boss,"사장님","2017.09.13.07:38",R.drawable.coffeebean,
                "원두 바뀜!!!",R.drawable.chat_green,"13"));





//        new LinearLayoutManager(getActivity().getApplicationContext())
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new AlbaTingAdapter(albaTingDataList,getActivity());
        recyclerView.setAdapter(adapter);

        return  view;
    }

}
