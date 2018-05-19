package com.example.albaalza.P_Chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;

import java.util.ArrayList;

/**
 * Created by jinyoungkim on 2018. 5. 17..
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private ArrayList<ChatItem>chatItems;
    private Context context;
    private String username;
    private int bookmark;

    public ChatAdapter(ArrayList<ChatItem> chatItems, Context context, String username,int bookmark) {
        this.chatItems = chatItems;
        this.context = context;
        this.username=username;
        this.bookmark=bookmark;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChatViewHolder holder, int position) {
        final ChatItem chatItem=chatItems.get(position);
        holder.groupname.setText(chatItem.getGroupname());
        holder.groupname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("chat_room_name", holder.groupname.getText().toString());
                intent.putExtra("chat_user_name",username);
                context.startActivity(intent);
            }
        });

//        북마크 기능
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chatItem.getBookmark()==0){
                    holder.bookmark.setImageResource(R.drawable.bookmark_on);
                    ApplicationController.getInstance().makeToast("북마크로 등록되었습니다.");
                    chatItem.setBookmark(1);
                }else if(chatItem.getBookmark()==1){
                    holder.bookmark.setImageResource(R.drawable.bookmark_off);
                    ApplicationController.getInstance().makeToast("북마크가 해제되었습니다.");
                    chatItem.setBookmark(0);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return chatItems.size();
    }


}
