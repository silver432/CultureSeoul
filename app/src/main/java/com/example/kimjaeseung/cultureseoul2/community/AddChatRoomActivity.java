package com.example.kimjaeseung.cultureseoul2.community;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.main.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kimjaeseung on 2017. 7. 22..
 */

public class AddChatRoomActivity extends Activity {
    @Bind(R.id.community_et_chatroomname)
    EditText chatRoomName;
    @Bind(R.id.community_btn_chatroomcreate)
    Button chatRoomCreateButton;
    @Bind(R.id.community_et_meetlocation)
    EditText meetLocation;
    @Bind(R.id.community_et_meetpeople)
    EditText meetPeople;
    @Bind(R.id.community_et_meettime)
    EditText meetTime;

    ChatRoomData mChatRoomData;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_addchatroom);
        ButterKnife.bind(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("room");


//        chatRoomCreateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put(chatRoomName.getText().toString(), "");
//                reference.updateChildren(map);
//                Intent intent=new Intent(AddChatRoomActivity.this,MainActivity.class);
//                intent.putExtra("select_page",CommunityFragment.class.getSimpleName());
//                startActivity(intent);
//            }
//        });
    }
    @OnClick({R.id.community_btn_chatroomcreate})
    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.community_btn_chatroomcreate:
                mChatRoomData = new ChatRoomData();
                mChatRoomData.setPerformanceImage(R.drawable.ic_launcher);
                mChatRoomData.setRoomLocation(meetLocation.getText().toString());
                mChatRoomData.setRoomName(chatRoomName.getText().toString());
                mChatRoomData.setRoomPeople(meetPeople.getText().toString());
                mChatRoomData.setRoomTime(meetTime.getText().toString());
                mChatRoomData.setRoomState("모집");

                mDatabaseReference.push().setValue(mChatRoomData);

                Intent intent = new Intent(AddChatRoomActivity.this,MainActivity.class);
//                intent.putExtra("room_information",mChatRoomData);
                intent.putExtra("select_page",CommunityFragment.class.getSimpleName());
                startActivity(intent);
                break;
        }
    }

}
