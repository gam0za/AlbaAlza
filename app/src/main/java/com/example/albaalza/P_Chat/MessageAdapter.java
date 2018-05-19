package com.example.albaalza.P_Chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albaalza.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by jinyoungkim on 2018. 5. 16..
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private ArrayList<MessageItem> messageItems;
    private Context context;

    public MessageAdapter(final ArrayList<MessageItem> messageItems, Context context){
        this.messageItems=messageItems;
        this.context=context;

        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageItems.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    messageItems.add(snapshot.getValue(MessageItem.class));
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        MessageItem messageItem=messageItems.get(position);
        holder.message_writer_image.setImageResource(R.drawable.user1);
        holder.message_writer.setText(messageItem.getMessage_writer());
        holder.message_text.setText(messageItem.getMessage_text());
        holder.message_time.setText(messageItem.getMessage_time());
    }

    @Override
    public int getItemCount() {
        return messageItems.size();
    }
}
