package com.example.albaalza.P_Chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albaalza.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView GroupName;
    private ImageView button_message,button_camera;
    private EditText edit_message;
    private ArrayList<MessageItem> messageItems=new ArrayList<>();
    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase;
    private String writer;
    private int i;
    String chat_room_name,chat_user_name,chat_message,chat_user,key;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        sharedPreferences=getSharedPreferences("account", Context.MODE_PRIVATE);
        writer=sharedPreferences.getString("id","doby");

        recyclerView=(RecyclerView)findViewById(R.id.message_recyclerview);
        GroupName=(TextView)findViewById(R.id.chat_name);
        button_camera=(ImageView)findViewById(R.id.button_camera);
        button_message=(ImageView)findViewById(R.id.button_message);
        edit_message=(EditText)findViewById(R.id.edit_message);


        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        messageItems.add(new MessageItem(writer,"hello"));//
        adapter=new MessageAdapter(messageItems,this);//
        recyclerView.setAdapter(adapter);//





//       ChatActivity로 부터 받아온 채팅방 이름과 user이름
        chat_room_name=getIntent().getStringExtra("chat_room_name");
        chat_user_name=getIntent().getStringExtra("chat_user_name");
        GroupName.setText(chat_room_name);



        button_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map=new HashMap<String,Object>();
                key=reference.push().getKey();
                reference.updateChildren(map);

                DatabaseReference root=reference.child(key);

                Map<String,Object>objectMap=new HashMap<>();

                objectMap.put("name",chat_user_name);
                objectMap.put("message",edit_message.getText().toString());

                root.updateChildren(objectMap);

                edit_message.setText("");
            }
        });

        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference(chat_room_name);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("ChildEventListener","onChildAdded");
                chatConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("ChildEventListener","onChildChanged");
                chatConversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void chatConversation(DataSnapshot dataSnapshot){
        Iterator i=dataSnapshot.getChildren().iterator();

        while(i.hasNext()){
            chat_message=(String)((DataSnapshot)i.next()).getValue();
            chat_user=(String)((DataSnapshot)i.next()).getValue();

            messageItems.add(new MessageItem(chat_user,chat_message));
        }

        adapter.notifyDataSetChanged();
    }


}


