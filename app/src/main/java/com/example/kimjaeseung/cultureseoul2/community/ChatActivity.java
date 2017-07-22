package com.example.kimjaeseung.cultureseoul2.community;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.kimjaeseung.cultureseoul2.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kimjaeseung on 2017. 7. 22..
 */

public class ChatActivity extends Activity{
    @Bind(R.id.community_chat_listview)
    ListView chatListView;
    @Bind(R.id.community_chat_edittext)
    EditText chatEditText;
    @Bind(R.id.community_chat_button)
    Button chatButton;

    private String chat_room_name, chat_user_name;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> room = new ArrayList<>();

    private DatabaseReference reference;
    private String key;
    private String chat_user;
    private String chat_message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_chat);
        ButterKnife.bind(this);

        chat_user_name = getIntent().getExtras().get("chat_user_name").toString();
        chat_room_name = getIntent().getExtras().get("chat_room_name").toString();

        setTitle(chat_room_name + " 채팅방");

        reference = FirebaseDatabase.getInstance().getReference().child(chat_room_name);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, room);
        chatListView.setAdapter(arrayAdapter);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                key = reference.push().getKey();

                reference.updateChildren(map);

                DatabaseReference root = reference.child(key);

                Map<String, Object> objectMap = new HashMap<String, Object>();

                objectMap.put("name", chat_user_name);
                objectMap.put("message", chatEditText.getText().toString());

                root.updateChildren(objectMap);

                chatEditText.setText("");
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
