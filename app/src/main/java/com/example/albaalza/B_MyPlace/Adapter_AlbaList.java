package com.example.albaalza.B_MyPlace;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albaalza.B_MyPlace.Server.AlbaListData;
import com.example.albaalza.B_MyPlace.Server.AlbaListPost;
import com.example.albaalza.B_MyPlace.Server.AlbaListResponse;
import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.Server.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jinyoungkim on 2018. 6. 23..
 */

public class Adapter_AlbaList extends RecyclerView.Adapter<ViewHolder_AlbaList> {

    private ArrayList<AlbaListData> albalistItem;
    private Context context;
    private String albalist_name;
    private int albalist_profile;
    private String accept;
    private String albalist_date;
    NetworkService networkService;
    AlbaListPost albaListPost;

    public Adapter_AlbaList(ArrayList<AlbaListData> albalistItem, Context context) {
        this.albalistItem = albalistItem;
        this.context = context;
    }

    @Override
    public ViewHolder_AlbaList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_albalist,parent,false);
        return new ViewHolder_AlbaList(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder_AlbaList holder, int position) {
        networkService= ApplicationController.getInstance().getNetworkService();

        final AlbaListData albaListResponse=albalistItem.get(position);
        holder.albalist_name.setText(albaListResponse.worker);
        holder.albalist_profile.setImageResource(R.drawable.albabot);
        holder.albalist_date.setText(albaListResponse.updated_at);

        if(albaListResponse.status==true){
            holder.accept.setImageResource(R.drawable.friend);
        }else{
            holder.accept.setImageResource(R.drawable.accept);
        }



    }

    @Override
    public int getItemCount() {
        return albalistItem.size();
    }
}



