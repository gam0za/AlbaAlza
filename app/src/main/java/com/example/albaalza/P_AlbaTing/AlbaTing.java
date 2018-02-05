package com.example.albaalza.P_AlbaTing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.albaalza.R;

import java.util.ArrayList;
import java.util.List;


public class AlbaTing extends Fragment  {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Item_AlbaTingList> albaTingLists = new ArrayList<>();

    public AlbaTing() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alba_ting, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_albatinglist);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter_AlbaTingList(albaTingLists, getActivity());
        recyclerView.setAdapter(adapter);

        albaTingLists.add(new Item_AlbaTingList("서울시 할리스 알바생 모임"));
        albaTingLists.add(new Item_AlbaTingList("CGV 종로점 매니저 모임"));
        albaTingLists.add(new Item_AlbaTingList("대한민국 카페 알바생 모임"));

//       리사이클러뷰 구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.recyclerview_line));
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }

}