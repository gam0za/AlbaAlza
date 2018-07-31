package com.example.albaalza.UI.P_Chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albaalza.R;

/**
 * Created by jinyoungkim on 2018. 5. 16..
 */

public class MessageViewHolder extends RecyclerView.ViewHolder{

    public TextView message_writer;
    public TextView message_time;
    public TextView message_text;
    public ImageView message_writer_image;

    public MessageViewHolder(View itemView) {
        super(itemView);
        message_writer=itemView.findViewById(R.id.message_writer);
        message_time=itemView.findViewById(R.id.message_time);
        message_text=itemView.findViewById(R.id.message_text);
        message_writer_image=itemView.findViewById(R.id.message_writer_image);
    }
}
