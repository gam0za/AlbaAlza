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

public class AlbaTing1Fragment extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton floating1;
    private String str2;
    public AlbaTing1Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup view= (ViewGroup) inflater.inflate(R.layout.fragment_alba_ting1,container,false);

        recyclerView = view.findViewById(R.id.recyclerview1);
        floating1= view.findViewById(R.id.floating1);
        floating1.attachToRecyclerView(recyclerView);



        floating1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(),AlbaTing_write.class);
                i.putExtra("albating","대타");
                startActivity(i);
            }
        });

        Context mContext=getActivity().getApplicationContext();



        List<AlbaTingData> albaTingDataList=new ArrayList<>();

        //리사이클러뷰에 뷰가 추가되는 순간
        //서버에서 가져온걸 붙이던가, 아니면 AlbaTingData 클래스에 서버에서 가져온 데이터를 추가해야 할 것 같다.

//        int profile, String name, String time, int image, String title, int comment_image, String comment

        albaTingDataList.add(new AlbaTingData(R.drawable.boss,"사장님","2017.03.13.07:38",R.drawable.holiday,
                "5월 황금연휴 대타 구합니다~",R.drawable.chat_green,"3"));

        albaTingDataList.add(new AlbaTingData(R.drawable.worker,"전지현","2017.05.13.07:38",R.drawable.recycler_alba,
                "6월 7일 저녁 6시!!!",R.drawable.chat_green,"5"));

        albaTingDataList.add(new AlbaTingData(R.drawable.example1,"강동원","2017.07.16.07:38",R.drawable.coffeebean,
                "동원찡이 대타가 급해요ㅠㅠ",R.drawable.chat_green,"3"));

        albaTingDataList.add(new AlbaTingData(R.drawable.example2,"김태희","2017.05.13.07:38",R.drawable.dfazz,
                "대타 급구!!!!!!!",R.drawable.chat_green,"3"));

        albaTingDataList.add(new AlbaTingData(R.drawable.example3,"송중기","2017.02.13.07:38",R.drawable.gudetama,
                "3월 대타 필요하신분~",R.drawable.chat_green,"3"));

        albaTingDataList.add(new AlbaTingData(R.drawable.worker,"사장님","2017.08.13.07:38",R.drawable.juhu,
                "나는야 주휴수당 챙겨주는 멋쟁이~",R.drawable.chat_green,"3"));



//        new LinearLayoutManager(getActivity().getApplicationContext())
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new AlbaTingAdapter(albaTingDataList,getActivity());
        recyclerView.setAdapter(adapter);


        return view;
    }


    @Override
    public void onClick(View v) {
        int index= recyclerView.getChildAdapterPosition(v);

        Intent intent= new Intent(getActivity().getApplicationContext(),RecyclerViewTest.class);
        startActivity(intent);

    }
}
