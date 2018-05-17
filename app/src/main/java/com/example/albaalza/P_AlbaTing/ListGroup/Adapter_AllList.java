package com.example.albaalza.P_AlbaTing.ListGroup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albaalza.R;

import java.util.ArrayList;

/**
 * Created by jinyoungkim on 2018. 5. 1..
 */

public class Adapter_AllList extends RecyclerView.Adapter<ViewHolder_AllList>{

    private ArrayList<ListData> item_albaTingAllLists;
    private Context context;

    public Adapter_AllList(ArrayList<ListData> item_albaTingAllLists, Context context) {
        this.item_albaTingAllLists = item_albaTingAllLists;
        this.context = context;
    }

    @Override
    public ViewHolder_AllList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alllist,parent,false);
        return new ViewHolder_AllList(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder_AllList holder, final int position) {
        final ListData allList= item_albaTingAllLists.get(position);
        holder.AlbaTing_Name.setText(allList.getGname());


    }

    @Override
    public int getItemCount() {
        return item_albaTingAllLists.size();
    }
}
