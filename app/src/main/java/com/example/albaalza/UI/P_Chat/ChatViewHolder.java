package com.example.albaalza.UI.P_Chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albaalza.R;

/**
 * Created by jinyoungkim on 2018. 5. 17..
 */

public class ChatViewHolder extends RecyclerView.ViewHolder {
    public TextView groupname;
    public ImageView bookmark;

    public ChatViewHolder(View itemView) {
        super(itemView);

        groupname=itemView.findViewById(R.id.group_name);
        bookmark=itemView.findViewById(R.id.bookmark);
    }
}
