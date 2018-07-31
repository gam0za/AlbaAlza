package com.example.albaalza.UI.P_MyPage;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.albaalza.UI.P_Login.Login;
import com.example.albaalza.R;

import static android.content.Context.MODE_PRIVATE;


public class MyPage extends Fragment {

    private LinearLayout asking,license,logout,delete;
    private Switch alarm_switch;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

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

        sharedPreferences=getContext().getSharedPreferences("account",MODE_PRIVATE);
        editor=sharedPreferences.edit();


//        라이센스
        license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),License.class));
            }
        });


//        로그아웃
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                Intent i =new Intent(getActivity(), Login.class);
                startActivity(i);
            }
        });

        return view;
    }

}