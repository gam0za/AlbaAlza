package com.example.albaalza.P_AlbaTing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albaalza.R;

import java.util.List;

/**
 * Created by DS on 2017-11-07.
 */

public class AlbaTingAdapter extends RecyclerView.Adapter<AlbaTingViewHolder>{

    private List<AlbaTingData> albaTingDataList;
    private Context context;
    private View.OnClickListener onClick;

    //생성자
    public AlbaTingAdapter(List<AlbaTingData> albaTingDataList, Context context) {
        this.albaTingDataList = albaTingDataList;
        this.context = context;
    }


    @Override
    public AlbaTingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview,parent,false);

        return new AlbaTingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AlbaTingViewHolder holder, int position) {
        AlbaTingData albaTingData=albaTingDataList.get(position);

        holder.profile.setImageResource(albaTingData.getProfile());
        holder.name.setText(albaTingData.getName());
        holder.time.setText(albaTingData.getTime());
        holder.image.setImageResource(albaTingData.getImage());
        holder.title.setText(albaTingData.getTitle());
        holder.comment_image.setImageResource(R.drawable.chat_green);
        holder.comment.setText(albaTingData.getComment());

        holder.itemView.setTag(albaTingData);
    }

    @Override
    public int getItemCount() {
        return albaTingDataList.size();
    }

    public void setOnItemClickListener(View.OnClickListener clickListener){
        onClick=clickListener;
    }
}
