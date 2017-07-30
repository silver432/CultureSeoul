package com.example.kimjaeseung.cultureseoul2.community;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kimjaeseung on 2017. 7. 22..
 */

public class ChatActivity extends Activity implements ChatAdapter.ChatAdapterOnClickHandler{
    @Bind(R.id.community_chat_recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.community_chat_edittext)
    EditText chatEditText;
    @Bind(R.id.community_chat_button)
    Button chatButton;


    List<ChatData> chatDataList=new ArrayList<>();
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ChatAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
    }
    private void initFirebaseDatabase(){
        reference = FirebaseDatabase.getInstance().getReference(chatRoomData.getFirebaseKey());
        reference.addChildEventListener(new ChildEventListener() {
            @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                chatData.firebaseKey = dataSnapshot.getKey();
                chatDataList.add(chatData);
                mAdapter.addItem(chatData);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
            }

            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
                String firebaseKey=dataSnapshot.getKey();
                int count = chatDataList.size();
                for (int i=0;i<count;i++){
                    if (chatDataList.get(i).firebaseKey.equals(firebaseKey)){
                        chatDataList.remove(i);
                        mAdapter.removeItem(i);
                        mAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(ChatData chatData) {

    }
}
