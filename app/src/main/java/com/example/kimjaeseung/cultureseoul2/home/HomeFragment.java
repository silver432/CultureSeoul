package com.example.kimjaeseung.cultureseoul2.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;
import com.example.kimjaeseung.cultureseoul2.main.MainActivity;
import com.example.kimjaeseung.cultureseoul2.performance.DetailActivity;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceFragment;
import com.example.kimjaeseung.cultureseoul2.performance.PerformanceGenreFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import kr.go.seoul.culturalevents.CulturalEventTypeMini;

/**
 * Created by kimjaeseung on 2017. 7. 11..
 */

public class HomeFragment extends Fragment implements HomeAdapter.HomeAdapterOnClickHandler, View.OnClickListener {
    private final static String TAG = "HomeFragment";
    private String openApiKey = "74776b4f6873696c34364a6368704d";

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private  HashMap<String, String> temp_performList = new HashMap<>();
    private  ArrayList<String> performList = new ArrayList<>();

    @Bind(R.id.firebaseText)TextView fbText;
    @Bind(R.id.firebaseEdit)EditText fbEdit;
    @Bind(R.id.firebaseButton)Button fbButton;

    @Bind(R.id.category_concert) public TextView category_concert;
    @Bind(R.id.category_classic) public TextView category_classic;
    @Bind(R.id.category_musical) public TextView category_musical;
    @Bind(R.id.category_theater) public TextView category_theater;
    @Bind(R.id.category_dancing) public TextView category_dancing;
    @Bind(R.id.category_exhibition) public TextView category_exhibition;
    @Bind(R.id.category_traditional_music) public TextView category_traditional_music;
    @Bind(R.id.category_festival) public TextView category_festival;
    @Bind(R.id.category_solo) public TextView category_solo;
    @Bind(R.id.category_movie) public TextView category_movie;
    @Bind(R.id.category_cultureclass) public TextView category_cultureclass;

    private String Genre;


    //@Bind(R.id.home_button_culturalevent) CulturalEventButtonTypeA culturalEventButtonTypeA;
    @Bind(R.id.home_button_culturalevent)
    CulturalEventTypeMini culturalEventTypeMini;

    public HomeFragment() {
    }

    public static Fragment getInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        culturalEventTypeMini.setOpenAPIKey(openApiKey);

        //이후 예외처리
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("perform");

        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMessage = fbEdit.getText().toString();
                myRef.setValue(newMessage);
            }
        });

        myRef.orderByValue().limitToFirst(5).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "key=" + dataSnapshot.getKey());
                Log.d(TAG, "value=" + dataSnapshot.getValue().toString());

                temp_performList.put(dataSnapshot.getKey(), dataSnapshot.getValue().toString());

                for ( String key : temp_performList.keySet() ) {
                    System.out.println("key : " + key +" / value : " + temp_performList.get(key));
                }

                Iterator it = sortByValue(temp_performList).iterator();

                System.out.println("------------sort 후 -------------");
                while(it.hasNext()) {
                    String temp = (String) it.next();
                    System.out.println(temp + " = " + temp_performList.get(temp));
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                temp_performList.put(dataSnapshot.getKey(), dataSnapshot.getValue().toString());
//                Log.d(TAG, "onChildChanged");
//                for ( String key : temp_performList.keySet() ) {
//                    System.out.println("key : " + key +" / value : " + temp_performList.get(key));
//                }


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                temp_performList.remove(dataSnapshot.getKey());
//                Log.d(TAG, "onChildRemoved");
//                for ( String key : temp_performList.keySet() ) {
//                    System.out.println("key : " + key +" / value : " + temp_performList.get(key));
//                }



            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        category_concert.setOnClickListener(this);
        category_concert.setTag(0);
        category_classic.setOnClickListener(this);
        category_classic.setTag(1);
        category_musical.setOnClickListener(this);
        category_musical.setTag(2);
        category_theater.setOnClickListener(this);
        category_theater.setTag(3);
        category_dancing.setOnClickListener(this);
        category_dancing.setTag(4);
        category_exhibition.setOnClickListener(this);
        category_exhibition.setTag(5);
        category_traditional_music.setOnClickListener(this);
        category_traditional_music.setTag(6);
        category_festival.setOnClickListener(this);
        category_festival.setTag(7);
        category_solo.setOnClickListener(this);
        category_solo.setTag(8);
        category_movie.setOnClickListener(this);
        category_movie.setTag(9);
        category_cultureclass.setOnClickListener(this);
        category_cultureclass.setTag(10);

    }

    public void printLog(HashMap<String, String> map) {
        Iterator<String> iterator = map.keySet().iterator();
        // 반복자를 이용해서 출력
        while (iterator.hasNext()) {
            String key = (String) iterator.next(); // 키 얻기
            System.out.print("key=" + key + " / value=" + map.get(key));  // 출력
        }
    }

    public static List sortByValue(final Map map) {
        List<String> list = new ArrayList();
        list.addAll(map.keySet());

        Collections.sort(list,new Comparator() {

            public int compare(Object o1,Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);

                return ((Comparable) v2).compareTo(v1);
            }

        });
        Collections.reverse(list);
        return list;
    }

    @Override
    public void onClick(CultureEvent cultureEvent) {
        Intent startToDetailActivity = new Intent(getActivity(), DetailActivity.class);
        startToDetailActivity.putExtra("key", cultureEvent);
        startActivity(startToDetailActivity);
    }

    @Override
    public void onClick(View v) {
        String textId = ((TextView) v).getText().toString();
        int categoryTag = (int)v.getTag();
        PerformanceGenreFragment.setmGenreTitle(categoryTag, textId);
        PerformanceFragment.getPageFlag();

        Intent intent1 = new Intent(this.getContext(), MainActivity.class);
        intent1.putExtra("select_page", TAG);
        startActivity(intent1);
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_nav_profile,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home_signout:
                signOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        Log.d(TAG,"sign out");
        Intent i = new Intent(getContext(),LoginActivity.class);
        startActivity(i);
    }
    */
}
