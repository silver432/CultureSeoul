package com.example.kimjaeseung.cultureseoul2.community;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;
import com.example.kimjaeseung.cultureseoul2.main.MainActivity;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceRealTimeFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kimjaeseung on 2017. 7. 22..
 */

public class AddChatRoomActivity extends Activity implements OnMapReadyCallback{
    private static final String TAG = AddChatRoomActivity.class.getSimpleName();

    @Bind(R.id.community_np_people)
    NumberPicker numberPickerPeople;


    private ChatRoomData mChatRoomData;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private CultureEvent cultureEvent;
    private static int mYear,mMonth,mDay,mHour,mMinute;
    private static String AM_PM;
    private int meetPeople;
    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_addchatroom);
        ButterKnife.bind(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("room");

        cultureEvent=(CultureEvent) getIntent().getSerializableExtra("key");
        initNumberPickerPeople();
        initGoogleMap();

    }
    @OnClick({R.id.community_btn_chatroomcreate,R.id.community_btn_select_performance,R.id.community_btn_select_day,R.id.community_btn_select_time})
    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.community_btn_chatroomcreate:
                mChatRoomData = new ChatRoomData();
                mChatRoomData.setPerformanceImage(cultureEvent.getMainImg().toLowerCase());
//                mChatRoomData.setRoomLocation(meetLocation.getText().toString());
                mChatRoomData.setRoomName(cultureEvent.getTitle());
                mChatRoomData.setRoomPeople("0/"+meetPeople);
                mChatRoomData.setRoomDay(mYear+"-"+mMonth+"-"+mDay);
                mChatRoomData.setRoomTime(AM_PM+" "+mHour+":"+mMinute);

                mDatabaseReference.push().setValue(mChatRoomData);

                Intent intent = new Intent(AddChatRoomActivity.this,MainActivity.class);
                intent.putExtra("select_page",CommunityFragment.class.getSimpleName());
                startActivity(intent);
                break;
            case R.id.community_btn_select_performance:
                Intent intent1 = new Intent(AddChatRoomActivity.this,MainActivity.class);
                intent1.putExtra("select_page", PerformanceRealTimeFragment.class.getSimpleName());
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
    private void initNumberPickerPeople(){
        numberPickerPeople.setMinValue(1);
        numberPickerPeople.setMaxValue(5);
        numberPickerPeople.setValue(1);
        numberPickerPeople.setWrapSelectorWheel(false);
        numberPickerPeople.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                meetPeople=newVal;
            }
        });
    }
    private void initGoogleMap(){
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.community_map_meetlocation);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        mGoogleMap.addMarker(markerOptions);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }
}
