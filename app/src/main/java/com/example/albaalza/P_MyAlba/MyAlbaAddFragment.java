package com.example.albaalza.P_MyAlba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.albaalza.R;


public class MyAlbaAddFragment extends Fragment {

    ImageView add_alba;
    public MyAlbaAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_my_alba_add, container, false);
        add_alba = (ImageView) view.findViewById(R.id.add_alba);
        add_alba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddAlba.class);
                getActivity().startActivityForResult(intent,1001);
            }
        });
        return view;
    }
}
