package com.example.albaalza.P_AlbaTing;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.albaalza.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jinyoung on 2018-02-05.
 */

public class Adapter_AlbaTingList extends Adapter<ViewHolder_AlbaTingList>{

    private ArrayList<Item_AlbaTingList> item_albaTingList;
    private Context context;

//    constructor
    public Adapter_AlbaTingList(ArrayList<Item_AlbaTingList> item_albaTingList, Context context) {
        this.item_albaTingList = item_albaTingList;
        this.context = context;
    }

    @Override
    public ViewHolder_AlbaTingList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_albatinglist,parent,false);
        return new ViewHolder_AlbaTingList(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder_AlbaTingList holder, final int position) {
        Item_AlbaTingList albaTingList=item_albaTingList.get(position);
        holder.AlbaTing_Name.setText(albaTingList.getAlbaTing_Name());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AlbaTing_Detail.class);
                String albating_name=holder.AlbaTing_Name.getText().toString();
                Log.d("넘겨주는 값",albating_name);
                intent.putExtra("albating_name",albating_name);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item_albaTingList.size();
    }



}
