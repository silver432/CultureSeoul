package com.example.kimjaeseung.cultureseoul2.community;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.utils.DateUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kimjaeseung on 2017. 9. 6..
 */

public class CommunityDateFragment extends Fragment implements ChatRoomAdapter.ChatRoomAdapterOnClickHandler {
    private static final String TAG = CommunityDateFragment.class.getSimpleName();
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ChatRoomAdapter mAdapter;
    private List<ChatRoomData> chatRoomDataList = new ArrayList<>();
    private int mYear = 0, mMonth = 0, mDay = 0;
    private static String mCurDate;

    @Bind(R.id.community_date_recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.community_date_button)
    Button dateButton;

    public CommunityDateFragment() {
    }

    public static Fragment getInstance() {
        CommunityDateFragment communityDateFragment = new CommunityDateFragment();
        return communityDateFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_date, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initButton();
        initFirebase();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabaseReference.removeEventListener(mChildEventListener);
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ChatRoomAdapter(this.getContext(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initButton(){
        Calendar calendar = new GregorianCalendar();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH) + 1;
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        dateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), mDateSetListener, mYear, mMonth - 1, mDay).show();

            }
        });
        if (mCurDate==null){
            mCurDate = String.valueOf(mYear + "-" + formatDay(mMonth) + "-" + formatDay(mDay));
        }

        dateButton.setText(mCurDate);
    }

    private void initFirebase() {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("room");

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatRoomData chatRoomData = dataSnapshot.getValue(ChatRoomData.class);
                if (chatRoomData.getRoomDay().equals(mCurDate)){
                    chatRoomData.setFirebaseKey(dataSnapshot.getKey());
                    chatRoomData.setRoomPeople((int) dataSnapshot.child("people").getChildrenCount());
                    HashMap<String, String> userList = new HashMap<>();
                    for (DataSnapshot user : dataSnapshot.child("people").getChildren()) {
                        userList.put(user.getKey(), user.getValue().toString());
                    }
                    chatRoomData.setPeopleList(userList);
                    chatRoomDataList.add(chatRoomData);
                    mAdapter.addItem(chatRoomData);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String firebaseKey = dataSnapshot.getKey();
                int count = chatRoomDataList.size();
                for (int i = 0; i < count; i++) {
                    if (chatRoomDataList.get(i).getFirebaseKey().equals(firebaseKey)) {
                        chatRoomDataList.remove(i);
                        mAdapter.removeItem(i);
                        mAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month + 1;
            mDay = dayOfMonth;

            mCurDate = String.valueOf(mYear + "-" + formatDay(mMonth) + "-" + formatDay(mDay));
            dateButton.setText(mCurDate);

            chatRoomDataList.clear();
            mAdapter.removeItemList();
            mAdapter.notifyDataSetChanged();

            initFirebase();
        }
    };

    @Override
    public void onClick(final ChatRoomData chatRoomData) {
        if (isUserInChatRoom(chatRoomData, mUser)) gotoChatActivity(chatRoomData);
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getContext());
            alertDialogBuilder.setTitle("방정보");
            alertDialogBuilder.setMessage(
                    "-방이름: " + chatRoomData.getRoomName() + "\n"
                            + "-공연이름: " + chatRoomData.getPerformanceName() + "\n"
                            + "-모임장소: " + chatRoomData.getRoomLocationName() + "(" + chatRoomData.getRoomLocation() + ")\n"
                            + "-모임날짜: " + chatRoomData.getRoomDay() + "\n"
                            + "-모임시간: " + chatRoomData.getRoomTime() + "\n"
                            + "-모임인원: " + chatRoomData.getRoomPeople())
                    .setPositiveButton("입장", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            mDatabaseReference.child(chatRoomData.getFirebaseKey()).child("people").push().setValue(mUser.getUid());
                            gotoChatActivity(chatRoomData);
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).create().show();
        }
    }

    private boolean isUserInChatRoom(ChatRoomData chatRoomdData, FirebaseUser mUser) {

        Set<Map.Entry<String, String>> set = chatRoomdData.getPeopleList().entrySet();
        Iterator<Map.Entry<String, String>> it = set.iterator();

        while (it.hasNext()) {
            Map.Entry<String, String> e = it.next();
            if (mUser.getUid().equals(e.getValue())) {
                return true;
            }
        }
        return false;
    }

    private void gotoChatActivity(ChatRoomData chatRoomData) {
        Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra("room_information", chatRoomData);
        startActivity(intent);
    }

    private String formatDay(int day) {
        String dayString;
        if (day < 10) dayString = "0" + day;
        else dayString = Integer.toString(day);
        return dayString;
    }
}
