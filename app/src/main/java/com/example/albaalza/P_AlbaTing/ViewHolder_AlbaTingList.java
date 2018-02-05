package com.example.albaalza.P_AlbaTing;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.example.albaalza.R;

/**
 * Created by Jinyoung on 2018-02-05.
 */

public class ViewHolder_AlbaTingList extends ViewHolder {
    public TextView     AlbaTing_Name;
    public ViewHolder_AlbaTingList(View itemView) {
        super(itemView);
        AlbaTing_Name=itemView.findViewById(R.id.albating_name); //알바팅 리스트의 알바팅 이름

    }
}
