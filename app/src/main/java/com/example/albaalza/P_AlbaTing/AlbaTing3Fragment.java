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


public class AlbaTing3Fragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton floating3;

    public AlbaTing3Fragment() {
        // Required empty public constructor
    }

    public static AlbaTing3Fragment newInstance(){
        Bundle args = new Bundle();

        AlbaTing3Fragment fragment=new AlbaTing3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view=(ViewGroup) inflater.inflate(R.layout.fragment_alba_ting3, container, false);

        recyclerView = view.findViewById(R.id.recyclerview3);
        floating3= view.findViewById(R.id.floating3);
        floating3.attachToRecyclerView(recyclerView);

        floating3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(),AlbaTing_write.class);
                i.putExtra("albating","이벤트");
                startActivity(i);
            }
        });

        Context mContext=getActivity().getApplicationContext();

        List<AlbaTingData> albaTingDataList=new ArrayList<>();

        //리사이클러뷰에 뷰가 추가되는 순간
        //서버에서 가져온걸 붙이던가, 아니면 AlbaTingData 클래스에 서버에서 가져온 데이터를 추가해야 할 것 같다.

//        int profile, String name, String time, int image, String title, int comment_image, String comment
        albaTingDataList.add(new AlbaTingData(R.drawable.boss,"사장님","2017.09.13.07:38",R.drawable.event1,
                "★알바몬 이벤트★",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.worker,"알바생","2017.09.5.11:11",R.drawable.event2,
                "알바 꽃길만 걷자~~",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.example1,"알바생","2017.09.5.11:11",R.drawable.event3,
                "친구야, 알바천국이 니 앱이다!",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.example2,"알바생","2017.09.5.11:11",R.drawable.event4,
                "※달려라 청춘아 4기 모집※",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.boss,"사장님","2017.09.13.07:38",R.drawable.event5,
                "전자근로계약서 쓰면된다!!",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.worker,"알바생","2017.09.5.11:11",R.drawable.event6,
                "★알바몬 이벤트★",R.drawable.chat_green,"13"));

        albaTingDataList.add(new AlbaTingData(R.drawable.example1,"알바생","2017.09.5.11:11",R.drawable.event7,
                "♡알바의 정석 이벤트♡",R.drawable.chat_green,"13"));



//        new LinearLayoutManager(getActivity().getApplicationContext())
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new AlbaTingAdapter(albaTingDataList,getActivity());
        recyclerView.setAdapter(adapter);


        return  view;
    }

}
