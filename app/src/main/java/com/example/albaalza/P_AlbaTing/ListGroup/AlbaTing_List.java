package com.example.albaalza.P_AlbaTing.ListGroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.albaalza.R;

import java.util.ArrayList;

public class AlbaTing_List extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Item_AlbaTingAllList> albaTingAllLists=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alba_ting__list);

        /*************************알바알자의 전체 알바팅 리스트 불러오기*************************/
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview_allList);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter_AllList(albaTingAllLists,this);
        recyclerView.setAdapter(adapter);

        albaTingAllLists.add(new Item_AlbaTingAllList("도비는 자유의 몸이에요"));
        albaTingAllLists.add(new Item_AlbaTingAllList("집에 가고 싶어요"));
        albaTingAllLists.add(new Item_AlbaTingAllList("수면카페 가고 싶어요"));

//      리사이클러뷰 구분선 추가
        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.recyclerview_line));
        recyclerView.addItemDecoration(dividerItemDecoration);

    }
}
