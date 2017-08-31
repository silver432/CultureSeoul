package com.example.kimjaeseung.cultureseoul2.community;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.google.firebase.database.ValueEventListener;
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
 * Created by kimjaeseung on 2017. 7. 11..
 */

public class CommunityFragment extends Fragment implements ChatRoomAdapter.ChatRoomAdapterOnClickHandler {
    private final static String TAG = CommunityFragment.class.getSimpleName();

    private FirebaseUser mUser;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ChatRoomAdapter mAdapter;
    private List<ChatRoomData> chatRoomDataList = new ArrayList<>();
    @Bind(R.id.community_chatroom_recyclerview)
    RecyclerView mRecyclerView;

    @Bind(R.id.community_chatroom_fab)
    FloatingActionButton floatingActionButton;

    public CommunityFragment() {
    }

    public static Fragment getInstance() {
        CommunityFragment communityFragment = new CommunityFragment();
        return communityFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initFirebase();
    }

    @OnClick({R.id.community_chatroom_fab})
    public void mOnClick(View view) {
        switch (view.getId()) {
            case R.id.community_chatroom_fab:
                Intent intent = new Intent(getContext(), AddChatRoomActivity.class);
                startActivity(intent);
                break;
        }
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
}