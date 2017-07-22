package com.example.kimjaeseung.cultureseoul2.community;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kimjaeseung.cultureseoul2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

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

    private ArrayList<String> room = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

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

                break;
        }
    }
}
