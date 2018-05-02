package com.example.albaalza.P_AlbaTing.ListGroup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albaalza.P_AlbaTing.ListPost.AlbaTing_Detail;
import com.example.albaalza.R;

import java.util.ArrayList;

/**
 * Created by jinyoungkim on 2018. 5. 1..
 */

public class Adapter_AllList extends RecyclerView.Adapter<ViewHolder_AllList>{

    private ArrayList<Item_AlbaTingAllList> item_albaTingAllLists;
    private Context context;

    public Adapter_AllList(ArrayList<Item_AlbaTingAllList> item_albaTingAllLists, Context context) {
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
        Item_AlbaTingAllList allList= item_albaTingAllLists.get(position);
        holder.AlbaTing_Name.setText(allList.getAlbaTing_Name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,AlbaTing_Detail.class);
                String albating_name=holder.AlbaTing_Name.getText().toString();
                Log.d("넘겨주는 값",albating_name);
                intent.putExtra("albating_name",albating_name);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item_albaTingAllLists.size();
    }
}
