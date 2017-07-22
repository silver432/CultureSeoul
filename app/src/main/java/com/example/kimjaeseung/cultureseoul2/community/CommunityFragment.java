package com.example.kimjaeseung.cultureseoul2.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kimjaeseung.cultureseoul2.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kimjaeseung on 2017. 7. 11..
 */

public class CommunityFragment extends Fragment {
    private final static String TAG = CommunityFragment.class.getSimpleName();

    @Bind(R.id.community_chatroom_fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.community_chatromm_listview)
    ListView chatRoomListView;

    private String userName="DefaultUser";
    private ArrayList<String> room = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private DatabaseReference reference = FirebaseDatabase.getInstance()
            .getReference().getRoot();

    public CommunityFragment(){}

    public static Fragment getInstance(){
        CommunityFragment communityFragment = new CommunityFragment();
        return communityFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community,container,false);
        ButterKnife.bind(this,view);

        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, room);
        chatRoomListView.setAdapter(arrayAdapter);
        floatingActionButton.attachToListView(chatRoomListView);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
                }

                room.clear();
                room.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        chatRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("chat_room_name", ((TextView) view).getText().toString());
                intent.putExtra("chat_user_name", userName);
                startActivity(intent);
            }
        });

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.community_chatroom_fab})
    public void mOnClick(View view){
        switch (view.getId()){
            case R.id.community_chatroom_fab:
                Intent intent = new Intent(getContext(),AddChatRoomActivity.class);
                startActivity(intent);
                break;
        }
    }
}
