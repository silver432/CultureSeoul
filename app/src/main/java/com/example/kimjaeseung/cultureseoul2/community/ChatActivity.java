package com.example.kimjaeseung.cultureseoul2.community;

import android.app.Activity;
import android.content.Intent;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


    private DatabaseReference reference;
    private ChatAdapter mAdapter;

    ChatRoomData chatRoomData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_chat);
        ButterKnife.bind(this);

        initView();
        initFirebaseDatabase();
    }
    private void initView(){
        Intent intent = getIntent();
        chatRoomData=(ChatRoomData)intent.getSerializableExtra("room_information");

        setTitle(chatRoomData.getRoomName() + " 채팅방");

        mAdapter = new ChatAdapter(this,R.layout.community_listitem_chat);
        chatListView.setAdapter(mAdapter);
    }
    private void initFirebaseDatabase(){
        reference = FirebaseDatabase.getInstance().getReference(chatRoomData.getFirebaseKey());
        reference.addChildEventListener(new ChildEventListener() {
            @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                chatData.firebaseKey = dataSnapshot.getKey();
                mAdapter.add(chatData);
                chatListView.smoothScrollToPosition(mAdapter.getCount());
            }

            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
                String firebaseKey=dataSnapshot.getKey();
                int count = mAdapter.getCount();
                for (int i=0;i<count;i++){
                    if (mAdapter.getItem(i).firebaseKey.equals(firebaseKey)){
                        mAdapter.remove(mAdapter.getItem(i));
                        break;
                    }
                }
            }

            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @OnClick({R.id.community_chat_button})
    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.community_chat_button:
                Calendar calendar = Calendar.getInstance();
                long now = calendar.getTimeInMillis();

                ChatData chatData = new ChatData();
                chatData.userPhoto=R.drawable.smile_50dp;
                chatData.message=chatEditText.getText().toString();
                chatData.userName="Default User";
                chatData.time=now;

                reference.push().setValue(chatData);

                chatEditText.setText("");
                break;
        }
    }
}
