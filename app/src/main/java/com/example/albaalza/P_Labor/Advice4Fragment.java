package com.example.albaalza.P_Labor;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albaalza.R;


public class Advice4Fragment extends Fragment {

    TextView text_btn;

    public Advice4Fragment() {
        // Required empty public constructor
    }

    public static Advice4Fragment newInstance() {
        Advice4Fragment fragment = new Advice4Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_advice4, container, false);

        text_btn = (TextView) view.findViewById(R.id.text_btn);
        text_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.moel.go.kr/index.do"));
                startActivity(intent);
            }
        });

        return view;
    }
}