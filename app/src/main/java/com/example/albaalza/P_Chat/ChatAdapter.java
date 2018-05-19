package com.example.albaalza.P_Chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences preferences;

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
    public void onBindViewHolder(final ChatViewHolder holder, final int position) {

        preferences=context.getSharedPreferences("bookmark",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=preferences.edit();
//        북마크 flag 저장
        if(preferences.getInt(chatItems.get(position).getGroupname(),chatItems.get(position).getBookmark())==0){
            holder.bookmark.setImageResource(R.drawable.bookmark_off);
        }else{
            holder.bookmark.setImageResource(R.drawable.bookmark_on);
        }

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
                    editor.putInt(chatItems.get(position).getGroupname(),chatItems.get(position).getBookmark());
                    editor.commit();

                }else if(chatItem.getBookmark()==1){
                    holder.bookmark.setImageResource(R.drawable.bookmark_off);
                    ApplicationController.getInstance().makeToast("북마크가 해제되었습니다.");
                    chatItem.setBookmark(0);
                    editor.putInt(chatItems.get(position).getGroupname(),chatItems.get(position).getBookmark());
                    editor.commit();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return chatItems.size();
    }


}
