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

    private ArrayList<MyGroupData> myGroupDataArrayList;

    private final View.OnClickListener clickListener;
    private NetworkService networkService;
    private MyGroupListPost myGroupListPost;

//    constructor
    public Adapter_AlbaTingList(ArrayList<MyGroupData> myGroupDataArrayList, View.OnClickListener clickListener,Context context) {
        this.myGroupDataArrayList = myGroupDataArrayList;
        this.clickListener=clickListener;
    }

    @Override
    public ViewHolder_AlbaTingList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_albatinglist,parent,false);
        view.setOnClickListener(clickListener);
        return new ViewHolder_AlbaTingList(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder_AlbaTingList holder, final int position) {

//        final Item_AlbaTingList albaTingList=item_albaTingList.get(position);
//        holder.AlbaTing_Name.setText(albaTingList.getAlbaTing_Name());
        Log.d("onBindViewHolder","들어옴");
        networkService=ApplicationController.getInstance().getNetworkService();

        myGroupListPost=new MyGroupListPost("abaz");
        Call<MyGroupListResponse> myGroupListResponseCall=networkService.myGroup(myGroupListPost);
        myGroupListResponseCall.enqueue(new Callback<MyGroupListResponse>() {
            @Override
            public void onResponse(Call<MyGroupListResponse> call, Response<MyGroupListResponse> response) {
                Log.d("result","onResponse");
                if(response.isSuccessful()){
                    myGroupDataArrayList=response.body().myGroupData;
                    holder.AlbaTing_Name.setText(response.body().myGroupData.get(position).gname);
                    ApplicationController.getInstance().makeToast("성공");
                    Log.d("result","SUCCESS");
                }
            }

            @Override
            public void onFailure(Call<MyGroupListResponse> call, Throwable t) {
                ApplicationController.getInstance().makeToast("실패");
                Log.d("result","FAIL");
            }

        });

    }

    @Override
    public int getItemCount() {
        return myGroupDataArrayList.size();
    }


}
