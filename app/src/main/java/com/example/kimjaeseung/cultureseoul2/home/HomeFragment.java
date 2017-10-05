package com.example.kimjaeseung.cultureseoul2.home;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.community.ChatRoomData;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;
import com.example.kimjaeseung.cultureseoul2.main.MainActivity;
import com.example.kimjaeseung.cultureseoul2.performance.DetailActivity;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceFragment;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceGenreFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import kr.go.seoul.culturalevents.CulturalEventTypeMini;

/**
 * Created by kimjaeseung on 2017. 7. 11..
 */

public class HomeFragment extends Fragment {
    private final static String TAG = "HomeFragment";
    private String openApiKey = "74776b4f6873696c34364a6368704d";

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("room");
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private ChildEventListener mChildEventListener;
    private ValueEventListener mValueEventListener;
    private List<ChatRoomData> chatRoomDataList = new ArrayList<>();

    @Bind(R.id.home_button_culturalevent)
    CulturalEventTypeMini culturalEventTypeMini;
    @Bind(R.id.ll_home_rank)
    LinearLayout linearLayout;

    public HomeFragment() {
    }

    public static Fragment getInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        culturalEventTypeMini.setOpenAPIKey(openApiKey);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFirebase();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabaseReference.removeEventListener(mChildEventListener);
        mDatabaseReference.removeEventListener(mValueEventListener);
    }

    private void initFirebase() {
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatRoomData chatRoomData = dataSnapshot.getValue(ChatRoomData.class);
                chatRoomDataList.add(chatRoomData);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                initView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
        mDatabaseReference.addValueEventListener(mValueEventListener);
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        HashMap<String, Integer> map = new HashMap<>();

        for (ChatRoomData c : chatRoomDataList) {
            Integer count = map.get(c.getPerformanceCode());
            map.put(c.getPerformanceCode(), (count == null) ? 1 : count + 1);
        }
        Object[] a = map.entrySet().toArray();

        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });
        int sizeOfRank = a.length > 5 ? 5:a.length;
        for (int i = 0; i < sizeOfRank; i++) {
            ChatRoomData mCrd=null;
            for (ChatRoomData crd:chatRoomDataList){
                if (crd.getPerformanceCode().equals(((Map.Entry<String, Integer>) a[i]).getKey())){
                    mCrd=crd;
                    break;
                }
            }
            View view = layoutInflater.inflate(R.layout.home_rank, null, false);
            TextView rankNum = (TextView)view.findViewById(R.id.tv_home_rank_num);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_home_rank_image);
            TextView rankName = (TextView) view.findViewById(R.id.tv_home_rank_perform_name);

            if (mCrd!=null){
                rankNum.setText(String.valueOf(i+1));
                Picasso.with(view.getContext())
                        .load(mCrd.getPerformanceImage())
                        .error(R.drawable.smile_50dp)
                        .fit()
                        .into(iv);
                rankName.setText(mCrd.getPerformanceName());

                linearLayout.addView(view);
            }
        }

    }

}
