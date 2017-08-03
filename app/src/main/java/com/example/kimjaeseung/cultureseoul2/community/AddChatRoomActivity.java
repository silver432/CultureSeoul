package com.example.kimjaeseung.cultureseoul2.community;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;
import com.example.kimjaeseung.cultureseoul2.main.MainActivity;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    @Bind(R.id.community_et_meetlocation)
    EditText meetLocation;
    @Bind(R.id.community_et_meetpeople)
    EditText meetPeople;
    @Bind(R.id.community_et_meettime)
    EditText meetTime;

    private ChatRoomData mChatRoomData;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private CultureEvent cultureEvent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_addchatroom);
        ButterKnife.bind(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("room");

        cultureEvent=(CultureEvent) getIntent().getSerializableExtra("key");

    }
    @OnClick({R.id.community_btn_chatroomcreate,R.id.community_btn_select_performance})
    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.community_btn_chatroomcreate:
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String day = simpleDateFormat.format(date);

                mChatRoomData = new ChatRoomData();
//                mChatRoomData.setPerformanceImage(R.drawable.ic_launcher);
                mChatRoomData.setPerformanceImage(cultureEvent.getMainImg().toLowerCase());
                mChatRoomData.setRoomLocation(meetLocation.getText().toString());
                mChatRoomData.setRoomName(cultureEvent.getTitle());
                mChatRoomData.setRoomPeople(meetPeople.getText().toString());
                mChatRoomData.setRoomTime(meetTime.getText().toString());
                mChatRoomData.setRoomDay(day);

                mDatabaseReference.push().setValue(mChatRoomData);

                Intent intent = new Intent(AddChatRoomActivity.this,MainActivity.class);
                intent.putExtra("select_page",CommunityFragment.class.getSimpleName());
                startActivity(intent);
                break;
            case R.id.community_btn_select_performance:
                Intent intent1 = new Intent(AddChatRoomActivity.this,MainActivity.class);
                intent1.putExtra("select_page", PerformanceFragment.class.getSimpleName());
                intent1.putExtra("choose",AddChatRoomActivity.class.getSimpleName());
                startActivity(intent1);
                break;
        }
    }

}
