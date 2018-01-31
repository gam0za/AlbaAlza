package com.example.albaalza.P_AlbaTing;

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

public class AlbaTing4Fragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton floating4;

    public AlbaTing4Fragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view= (ViewGroup) inflater.inflate(R.layout.fragment_alba_ting4, container, false);

        recyclerView = view.findViewById(R.id.recyclerview4);
        floating4=view.findViewById(R.id.floating4);
        floating4.attachToRecyclerView(recyclerView);

        floating4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(),AlbaTing_write.class);
                i.putExtra("albating","잡담");
                startActivity(i);
            }
        });

        List<AlbaTingData> albaTingDataList=new ArrayList<>();

        //리사이클러뷰에 뷰가 추가되는 순간
        //서버에서 가져온걸 붙이던가, 아니면 AlbaTingData 클래스에 서버에서 가져온 데이터를 추가해야 할 것 같다.

//        int profile, String name, String time, int image, String title, int comment_image, String comment
        albaTingDataList.add(new AlbaTingData(R.drawable.boss,"사장님","2017.09.13.07:38",R.drawable.after,
                "9월 회식>_____<",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.worker,"알바생","2017.09.5.11:11",R.drawable.bossam,
                "회식 메뉴 투표!!",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.example1,"알바생","2017.09.5.11:11",R.drawable.after2,
                "회식 사진~~",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.example2,"알바생","2017.09.5.11:11",R.drawable.beer,
                "♥♡투썸플레이스 정자점 맥주번개>___<♡♥",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.boss,"사장님","2017.09.13.07:38",R.drawable.zonzal,
                "박 매니저님 송별회ㅠㅠ",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.worker,"알바생","2017.09.5.11:11",R.drawable.mamamoo,
                "신입알바 환영회",R.drawable.chat_green,"13"));



//        new LinearLayoutManager(getActivity().getApplicationContext())
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new AlbaTingAdapter(albaTingDataList,getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }

}
