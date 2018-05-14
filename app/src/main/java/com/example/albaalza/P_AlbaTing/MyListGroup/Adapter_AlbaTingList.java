package com.example.albaalza.P_AlbaTing.MyListGroup;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.Server.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jinyoung on 2018-02-05.
 */

public class Adapter_AlbaTingList extends Adapter<ViewHolder_AlbaTingList>{
    private ArrayList<MyGroupData> item_albaTingLists;
    private Context context;

    public Adapter_AlbaTingList(ArrayList<MyGroupData> myGroupDataArrayList, Context context) {
        this.item_albaTingLists = myGroupDataArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder_AlbaTingList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_albatinglist,parent,false);
        Log.d("CREATE","CREATE");
        return new ViewHolder_AlbaTingList(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder_AlbaTingList holder, final int position) {

        Log.d("BIND","BIND");

        MyGroupData albaTingList= item_albaTingLists.get(position);
        holder.AlbaTing_Name.setText(albaTingList.getGname());

    }

    @Override
    public int getItemCount() {
        try{
            return item_albaTingLists.size();
        }catch (Exception e){
            return 0;
        }

    }

    public void setItem(ArrayList<MyGroupData> myGroupData){
        this.item_albaTingLists=myGroupData;

        notifyDataSetChanged();
    }


}
