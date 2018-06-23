package com.example.albaalza.B_MyPlace;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albaalza.R;

/**
 * Created by jinyoungkim on 2018. 6. 23..
 */

public class ViewHolder_AlbaList extends RecyclerView.ViewHolder {

    public ImageView albalist_profile;
    public TextView albalist_name;
    public ImageView accept;
    public TextView albalist_date;

    public ViewHolder_AlbaList(View itemView) {
        super(itemView);

        albalist_profile=itemView.findViewById(R.id.albalist_profile);
        albalist_name=itemView.findViewById(R.id.albalist_name);
        accept=itemView.findViewById(R.id.accept);
        albalist_date=itemView.findViewById(R.id.albalist_date);
    }
}
