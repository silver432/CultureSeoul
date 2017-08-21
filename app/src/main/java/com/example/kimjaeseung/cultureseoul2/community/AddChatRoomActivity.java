package com.example.kimjaeseung.cultureseoul2.community;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;
import com.example.kimjaeseung.cultureseoul2.main.MainActivity;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceRealTimeFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;


import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kimjaeseung on 2017. 7. 22..
 */

public class AddChatRoomActivity extends FragmentActivity implements OnConnectionFailedListener{
    private static final String TAG = AddChatRoomActivity.class.getSimpleName();

    @Bind(R.id.community_np_people)
    NumberPicker numberPickerPeople;


    private ChatRoomData mChatRoomData;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private CultureEvent cultureEvent;
    private static int mYear,mMonth,mDay,mHour,mMinute,mPeople=1;
    private static String AM_PM,mLocation;
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_addchatroom);
        ButterKnife.bind(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("room");

        cultureEvent=(CultureEvent) getIntent().getSerializableExtra("key");

        initGoogleApiClient();
        initNumberPickerPeople();

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                mLocation = place.getName().toString();
            }
        }
    }

    @OnClick({R.id.community_btn_chatroomcreate,R.id.community_btn_select_performance,R.id.community_btn_select_day,R.id.community_btn_select_time,R.id.community_btn_select_location})
    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.community_btn_chatroomcreate:
                mChatRoomData = new ChatRoomData();
                mChatRoomData.setPerformanceImage(cultureEvent.getMainImg().toLowerCase());
                mChatRoomData.setRoomLocation(mLocation);
                mChatRoomData.setRoomName(cultureEvent.getTitle());
                mChatRoomData.setRoomPeople("0/"+mPeople);
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
            case R.id.community_btn_select_location:
                initPlacePicker();
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
        numberPickerPeople.setValue(mPeople);
        numberPickerPeople.setWrapSelectorWheel(false);
        numberPickerPeople.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mPeople=newVal;
            }
        });
    }
    private void initGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
    }
    private void initPlacePicker(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
