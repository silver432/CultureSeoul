package com.example.kimjaeseung.cultureseoul2.community;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kimjaeseung on 2017. 7. 22..
 */

public class AddChatRoomActivity extends Activity {
    @Bind(R.id.community_et_meetlocation)
    EditText meetLocation;
    @Bind(R.id.community_et_meetpeople)
    EditText meetPeople;


    private ChatRoomData mChatRoomData;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private CultureEvent cultureEvent;
    private static int mYear,mMonth,mDay,mHour,mMinute;
    private static String AM_PM;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_addchatroom);
        ButterKnife.bind(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("room");

        cultureEvent=(CultureEvent) getIntent().getSerializableExtra("key");

    }
    @OnClick({R.id.community_btn_chatroomcreate,R.id.community_btn_select_performance,R.id.community_btn_select_day,R.id.community_btn_select_time})
    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.community_btn_chatroomcreate:
//                long now = System.currentTimeMillis();
//                Date date = new Date(now);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                String day = simpleDateFormat.format(date);

                mChatRoomData = new ChatRoomData();
                mChatRoomData.setPerformanceImage(cultureEvent.getMainImg().toLowerCase());
                mChatRoomData.setRoomLocation(meetLocation.getText().toString());
                mChatRoomData.setRoomName(cultureEvent.getTitle());
                mChatRoomData.setRoomPeople("0/"+meetPeople.getText().toString());
                mChatRoomData.setRoomDay(mYear+"-"+mMonth+"-"+mDay);
                mChatRoomData.setRoomTime(AM_PM+" "+mHour+":"+mMinute);

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
            case R.id.community_btn_select_day:
                Calendar calendar = new GregorianCalendar();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(AddChatRoomActivity.this,mDateSetListener,mYear,mMonth,mDay).show();
                break;
            case R.id.community_btn_select_time:
                Calendar calendar1 = new GregorianCalendar();
                mHour = calendar1.get(Calendar.HOUR_OF_DAY);
                mMinute= calendar1.get(Calendar.MINUTE);
                new TimePickerDialog(AddChatRoomActivity.this,mTimeSetListener,mHour,mMinute,false).show();
                break;
        }
    }
    DatePickerDialog.OnDateSetListener mDateSetListener= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear=year;
            mMonth=month+1;
            mDay=dayOfMonth;
        }
    };
    TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (hourOfDay>12) {
                AM_PM="오후";
                hourOfDay-=12;
            }
            else {
                AM_PM="오전";
            }
            mHour=hourOfDay;
            mMinute=minute;
        }
    };

}
