package com.example.albaalza.P_MyPage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.albaalza.R;


public class MyPage extends Fragment {

    private LinearLayout asking,license,logout,delete;
    private Switch alarm_switch;

    public MyPage(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_my_page, container, false);

        asking=(LinearLayout)view.findViewById(R.id.asking);
        license=(LinearLayout)view.findViewById(R.id.license);
        logout=(LinearLayout)view.findViewById(R.id.logout);
        delete=(LinearLayout)view.findViewById(R.id.delete);
        alarm_switch=(Switch)view.findViewById(R.id.alarm_swith);



        return view;
    }

}