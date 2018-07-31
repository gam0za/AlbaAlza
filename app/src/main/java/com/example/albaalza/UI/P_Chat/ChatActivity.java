package com.example.albaalza.UI.P_Chat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatActivity extends AppCompatActivity implements  OnClickListener{

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText editText;
    private ImageView button;
    private int bookmark=0;

    private ArrayList<ChatItem> chatItems = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private String name,room_name,key;
    String writer;
    private ChildEventListener childEventListener;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        editText = (EditText) findViewById(R.id.editText);
        button = (ImageView) findViewById(R.id.button_chat);


        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //chatItems.add(new ChatItem("groupname",R.drawable.bookmark_off));//dummy data
        Log.d("username: ","jinyoung");   //jinyoung is dummy data

        sharedPreferences=getSharedPreferences("account",MODE_PRIVATE);
        writer=sharedPreferences.getString("id","doby");
        chatAdapter=new ChatAdapter(chatItems,this,writer,bookmark);
        recyclerView.setAdapter(chatAdapter);
        //       리사이클러뷰 구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),
                LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.recyclerview_line));
        recyclerView.addItemDecoration(dividerItemDecoration);

        initFirebaseDatabase();
        button.setOnClickListener(this);

    }

    private void getChatting(DataSnapshot dataSnapshot){
        String message=dataSnapshot.getValue(String.class);
        chatItems.add(new ChatItem(message,bookmark));

        chatAdapter.notifyDataSetChanged();

    }


    private void initFirebaseDatabase(){
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference("chat");
        childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getChatting(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getChatting(dataSnapshot);
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
        };

        reference.addChildEventListener(childEventListener);
    }





    @Override
    public void onClick(View view) {
        String message=editText.getText().toString();
        if(!TextUtils.isEmpty(message)){
            editText.setText("");
            reference.push().setValue(message);
        }




    }
}