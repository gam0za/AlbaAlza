package com.example.albaalza.P_Labor;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.albaalza.R;
import com.melnykov.fab.FloatingActionButton;

public class Advice1Fragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton floating1;

    private LinearLayout labor1, labor2, labor3, labor4, labor5, labor6, labor7;
    public Advice1Fragment() {
        // Required empty public constructor
    }

    public static Advice1Fragment newInstance() {
        Advice1Fragment fragment = new Advice1Fragment();
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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_advice1, container, false);

        labor1 = (LinearLayout) view.findViewById(R.id.labor1);
        labor2 = (LinearLayout) view.findViewById(R.id.labor2);
        labor3 = (LinearLayout) view.findViewById(R.id.labor3);
        labor4 = (LinearLayout) view.findViewById(R.id.labor4);
        labor5 = (LinearLayout) view.findViewById(R.id.labor5);
        labor6 = (LinearLayout) view.findViewById(R.id.labor6);
        labor7 = (LinearLayout) view.findViewById(R.id.labor7);

        clickListenerFactory();

        return view;
    }

    public void clickListenerFactory(){

        // 노동청
        labor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Advice1Activity.class);
                intent.putExtra("code", 1);
                Toast.makeText(getContext(), "s", Toast.LENGTH_SHORT).show();
                getActivity().startActivity(intent);
            }
        });

        //최저시급
        labor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Advice1Activity.class);
                intent.putExtra("code", 2);
                getActivity().startActivity(intent);
            }
        });

        // 4대보험
        labor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Advice1Activity.class);
                intent.putExtra("code", 3);
                getActivity().startActivity(intent);
            }
        });

        // 근로계약서
        labor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Advice1Activity.class);
                intent.putExtra("code", 4);
                getActivity().startActivity(intent);
            }
        });

        // 주휴수당
        labor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Advice1Activity.class);
                intent.putExtra("code", 5);
                getActivity().startActivity(intent);
            }
        });

        // 야간수당
        labor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Advice1Activity.class);
                intent.putExtra("code", 6);
                getActivity().startActivity(intent);
            }
        });

        //퇴직금
        labor7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Advice1Activity.class);
                intent.putExtra("code", 7);
                getActivity().startActivity(intent);
            }
        });

    }
}