package com.example.albaalza.P_Chat;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.albaalza.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChattingRoomActivity extends AppCompatActivity {

    ListView listView;
    EditText text;
    ImageView button;
    SharedPreferences sharedPreferences;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> chat_room=new ArrayList<>();

    private String chat_room_name,chat_user_name;

    private DatabaseReference reference;
    private String key;
    private String chat_user;
    private String chat_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);

        button=(ImageView)findViewById(R.id.button_room);
        text = (EditText)findViewById(R.id.edit_room);
        listView=(ListView)findViewById(R.id.list_room);

        chat_user_name = getIntent().getExtras().get("chat_user_name").toString();
        chat_room_name = getIntent().getExtras().get("chat_room_name").toString();

        setTitle(chat_room_name + " 채팅방");

        button.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View view) {

      Map<String, Object> map = new HashMap<String, Object>();
          key = reference.push().getKey();

                reference.updateChildren(map);

                DatabaseReference root = reference.child(key);

                Map<String, Object> objectMap = new HashMap<String, Object>();

                objectMap.put("name", chat_user_name);
                objectMap.put("message", text.getText().toString());

                root.updateChildren(objectMap);

                text.setText("");
            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chatConversation(dataSnapshot);
            }

            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                chatConversation(dataSnapshot);
            }

            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void chatConversation(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            chat_message = (String) ((DataSnapshot) i.next()).getValue();
            chat_user = (String) ((DataSnapshot) i.next()).getValue();

            arrayAdapter.add(chat_user + " : " + chat_message);
        }

        arrayAdapter.notifyDataSetChanged();
    }
}
