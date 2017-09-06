package com.example.kimjaeseung.cultureseoul2.community;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimjaeseung.cultureseoul2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kimjaeseung on 2017. 9. 6..
 */

public class CommunityRealTimeFragment extends Fragment implements ChatRoomAdapter.ChatRoomAdapterOnClickHandler,SearchView.OnQueryTextListener{
    private static final String TAG = CommunityRealTimeFragment.class.getSimpleName();
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ChatRoomAdapter mAdapter;
    private List<ChatRoomData> chatRoomDataList = new ArrayList<>();

    @Bind(R.id.community_realtime_recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.community_realtime_fab)
    FloatingActionButton floatingActionButton;

    public CommunityRealTimeFragment(){}
    public static Fragment getInstance()
    {
        CommunityRealTimeFragment communityRealTimeFragment = new CommunityRealTimeFragment();
        return communityRealTimeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_reatltime,container,false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initFirebase();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabaseReference.removeEventListener(mChildEventListener);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_performance, menu);
        MenuItem menuItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem); // 액션바에 searchview 추가
        searchView.setOnQueryTextListener(this);
    }

    @OnClick({R.id.community_realtime_fab})
    public void mOnClick(View view) {
        switch (view.getId()) {
            case R.id.community_realtime_fab:
                Intent intent = new Intent(getContext(), AddChatRoomActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ChatRoomAdapter(this.getContext(), this);
        mRecyclerView.setAdapter(mAdapter);
        floatingActionButton.attachToRecyclerView(mRecyclerView);
    }

    private void initFirebase() {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("room");

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatRoomData chatRoomData = dataSnapshot.getValue(ChatRoomData.class);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        List<ChatRoomData> newList = new ArrayList<>();
        for (ChatRoomData chatRoomData : chatRoomDataList)
        {
            String name = chatRoomData.getRoomName().toLowerCase();
            if (name.contains(newText))
                newList.add(chatRoomData);
        }

        mAdapter.setFilter(newList);

        return true;
    }
}
