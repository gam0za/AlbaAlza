package com.example.albaalza.P_AlbaTing.ListGroup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.albaalza.R;

/**
 * Created by jinyoungkim on 2018. 5. 1..
 */

public class ViewHolder_AllList extends RecyclerView.ViewHolder{
    public TextView AlbaTing_Name;
    public ViewHolder_AllList(View itemView) {
        super(itemView);
        AlbaTing_Name=itemView.findViewById(R.id.allList_name);

    }


}
